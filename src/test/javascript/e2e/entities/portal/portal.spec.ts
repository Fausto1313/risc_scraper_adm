/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PortalComponentsPage, PortalDeleteDialog, PortalUpdatePage } from './portal.page-object';

const expect = chai.expect;

describe('Portal e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let portalUpdatePage: PortalUpdatePage;
    let portalComponentsPage: PortalComponentsPage;
    let portalDeleteDialog: PortalDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Portals', async () => {
        await navBarPage.goToEntity('portal');
        portalComponentsPage = new PortalComponentsPage();
        expect(await portalComponentsPage.getTitle()).to.eq('riscScraperAdmApp.portal.home.title');
    });

    it('should load create Portal page', async () => {
        await portalComponentsPage.clickOnCreateButton();
        portalUpdatePage = new PortalUpdatePage();
        expect(await portalUpdatePage.getPageTitle()).to.eq('riscScraperAdmApp.portal.home.createOrEditLabel');
        await portalUpdatePage.cancel();
    });

    it('should create and save Portals', async () => {
        const nbButtonsBeforeCreate = await portalComponentsPage.countDeleteButtons();

        await portalComponentsPage.clickOnCreateButton();
        await promise.all([
            portalUpdatePage.setIdscrapyDataInput('5'),
            portalUpdatePage.setNameInput('name'),
            portalUpdatePage.setDomainInput('domain'),
            portalUpdatePage.setUrlInput('url'),
            portalUpdatePage.setHeaderPathInput('headerPath'),
            portalUpdatePage.setBodyPathInput('bodyPath'),
            portalUpdatePage.setAutorPathInput('autorPath'),
            portalUpdatePage.setDatePathInput('datePath'),
            portalUpdatePage.setResumePathInput('resumePath'),
            portalUpdatePage.setPathInput('path'),
            portalUpdatePage.setIdentifierInput('identifier'),
            portalUpdatePage.setUsuarioInput('usuario'),
            portalUpdatePage.setFechaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            portalUpdatePage.schedulerSelectLastOption(),
            portalUpdatePage.portalTypeSelectLastOption(),
            portalUpdatePage.customerSelectLastOption()
        ]);
        expect(await portalUpdatePage.getIdscrapyDataInput()).to.eq('5');
        expect(await portalUpdatePage.getNameInput()).to.eq('name');
        expect(await portalUpdatePage.getDomainInput()).to.eq('domain');
        expect(await portalUpdatePage.getUrlInput()).to.eq('url');
        expect(await portalUpdatePage.getHeaderPathInput()).to.eq('headerPath');
        expect(await portalUpdatePage.getBodyPathInput()).to.eq('bodyPath');
        expect(await portalUpdatePage.getAutorPathInput()).to.eq('autorPath');
        expect(await portalUpdatePage.getDatePathInput()).to.eq('datePath');
        expect(await portalUpdatePage.getResumePathInput()).to.eq('resumePath');
        expect(await portalUpdatePage.getPathInput()).to.eq('path');
        const selectedXpath = portalUpdatePage.getXpathInput();
        if (await selectedXpath.isSelected()) {
            await portalUpdatePage.getXpathInput().click();
            expect(await portalUpdatePage.getXpathInput().isSelected()).to.be.false;
        } else {
            await portalUpdatePage.getXpathInput().click();
            expect(await portalUpdatePage.getXpathInput().isSelected()).to.be.true;
        }
        expect(await portalUpdatePage.getIdentifierInput()).to.eq('identifier');
        expect(await portalUpdatePage.getUsuarioInput()).to.eq('usuario');
        expect(await portalUpdatePage.getFechaInput()).to.contain('2001-01-01T02:30');
        await portalUpdatePage.save();
        expect(await portalUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await portalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Portal', async () => {
        const nbButtonsBeforeDelete = await portalComponentsPage.countDeleteButtons();
        await portalComponentsPage.clickOnLastDeleteButton();

        portalDeleteDialog = new PortalDeleteDialog();
        expect(await portalDeleteDialog.getDialogTitle()).to.eq('riscScraperAdmApp.portal.delete.question');
        await portalDeleteDialog.clickOnConfirmButton();

        expect(await portalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
