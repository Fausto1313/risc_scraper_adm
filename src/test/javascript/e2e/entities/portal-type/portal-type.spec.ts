/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PortalTypeComponentsPage, PortalTypeDeleteDialog, PortalTypeUpdatePage } from './portal-type.page-object';

const expect = chai.expect;

describe('PortalType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let portalTypeUpdatePage: PortalTypeUpdatePage;
    let portalTypeComponentsPage: PortalTypeComponentsPage;
    let portalTypeDeleteDialog: PortalTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PortalTypes', async () => {
        await navBarPage.goToEntity('portal-type');
        portalTypeComponentsPage = new PortalTypeComponentsPage();
        expect(await portalTypeComponentsPage.getTitle()).to.eq('riscScraperAdmApp.portalType.home.title');
    });

    it('should load create PortalType page', async () => {
        await portalTypeComponentsPage.clickOnCreateButton();
        portalTypeUpdatePage = new PortalTypeUpdatePage();
        expect(await portalTypeUpdatePage.getPageTitle()).to.eq('riscScraperAdmApp.portalType.home.createOrEditLabel');
        await portalTypeUpdatePage.cancel();
    });

    it('should create and save PortalTypes', async () => {
        const nbButtonsBeforeCreate = await portalTypeComponentsPage.countDeleteButtons();

        await portalTypeComponentsPage.clickOnCreateButton();
        await promise.all([portalTypeUpdatePage.setNameInput('name'), portalTypeUpdatePage.setDescriptionInput('description')]);
        expect(await portalTypeUpdatePage.getNameInput()).to.eq('name');
        expect(await portalTypeUpdatePage.getDescriptionInput()).to.eq('description');
        await portalTypeUpdatePage.save();
        expect(await portalTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await portalTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PortalType', async () => {
        const nbButtonsBeforeDelete = await portalTypeComponentsPage.countDeleteButtons();
        await portalTypeComponentsPage.clickOnLastDeleteButton();

        portalTypeDeleteDialog = new PortalTypeDeleteDialog();
        expect(await portalTypeDeleteDialog.getDialogTitle()).to.eq('riscScraperAdmApp.portalType.delete.question');
        await portalTypeDeleteDialog.clickOnConfirmButton();

        expect(await portalTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
