/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PagesComponentsPage, PagesDeleteDialog, PagesUpdatePage } from './pages.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Pages e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let pagesUpdatePage: PagesUpdatePage;
    let pagesComponentsPage: PagesComponentsPage;
    let pagesDeleteDialog: PagesDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Pages', async () => {
        await navBarPage.goToEntity('pages');
        pagesComponentsPage = new PagesComponentsPage();
        expect(await pagesComponentsPage.getTitle()).to.eq('riscScraperAdmApp.pages.home.title');
    });

    it('should load create Pages page', async () => {
        await pagesComponentsPage.clickOnCreateButton();
        pagesUpdatePage = new PagesUpdatePage();
        expect(await pagesUpdatePage.getPageTitle()).to.eq('riscScraperAdmApp.pages.home.createOrEditLabel');
        await pagesUpdatePage.cancel();
    });

    it('should create and save Pages', async () => {
        const nbButtonsBeforeCreate = await pagesComponentsPage.countDeleteButtons();

        await pagesComponentsPage.clickOnCreateButton();
        await promise.all([
            pagesUpdatePage.setIdscrapyDataInput('5'),
            pagesUpdatePage.setHeaderDataInput('headerData'),
            pagesUpdatePage.setBodyDataInput('bodyData'),
            pagesUpdatePage.setAutorDataInput('autorData'),
            pagesUpdatePage.setDateDataInput('dateData'),
            pagesUpdatePage.setHeaderCleanInput('headerClean'),
            pagesUpdatePage.setBodyCleanInput('bodyClean'),
            pagesUpdatePage.setAutorCleanInput('autorClean'),
            pagesUpdatePage.setDateCleanInput('dateClean'),
            pagesUpdatePage.setResumeCleanInput('resumeClean'),
            pagesUpdatePage.setResumeDataInput('resumeData'),
            pagesUpdatePage.setScreenshotInput(absolutePath),
            pagesUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            pagesUpdatePage.setFullPathInput('fullPath'),
            pagesUpdatePage.setUrlInput('url'),
            pagesUpdatePage.portalSelectLastOption()
        ]);
        expect(await pagesUpdatePage.getIdscrapyDataInput()).to.eq('5');
        expect(await pagesUpdatePage.getHeaderDataInput()).to.eq('headerData');
        expect(await pagesUpdatePage.getBodyDataInput()).to.eq('bodyData');
        expect(await pagesUpdatePage.getAutorDataInput()).to.eq('autorData');
        expect(await pagesUpdatePage.getDateDataInput()).to.eq('dateData');
        expect(await pagesUpdatePage.getHeaderCleanInput()).to.eq('headerClean');
        expect(await pagesUpdatePage.getBodyCleanInput()).to.eq('bodyClean');
        expect(await pagesUpdatePage.getAutorCleanInput()).to.eq('autorClean');
        expect(await pagesUpdatePage.getDateCleanInput()).to.eq('dateClean');
        expect(await pagesUpdatePage.getResumeCleanInput()).to.eq('resumeClean');
        expect(await pagesUpdatePage.getResumeDataInput()).to.eq('resumeData');
        expect(await pagesUpdatePage.getScreenshotInput()).to.endsWith(fileNameToUpload);
        expect(await pagesUpdatePage.getDateInput()).to.contain('2001-01-01T02:30');
        expect(await pagesUpdatePage.getFullPathInput()).to.eq('fullPath');
        expect(await pagesUpdatePage.getUrlInput()).to.eq('url');
        await pagesUpdatePage.save();
        expect(await pagesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await pagesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Pages', async () => {
        const nbButtonsBeforeDelete = await pagesComponentsPage.countDeleteButtons();
        await pagesComponentsPage.clickOnLastDeleteButton();

        pagesDeleteDialog = new PagesDeleteDialog();
        expect(await pagesDeleteDialog.getDialogTitle()).to.eq('riscScraperAdmApp.pages.delete.question');
        await pagesDeleteDialog.clickOnConfirmButton();

        expect(await pagesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
