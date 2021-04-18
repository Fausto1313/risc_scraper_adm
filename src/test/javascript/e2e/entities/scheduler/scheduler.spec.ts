/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SchedulerComponentsPage, SchedulerDeleteDialog, SchedulerUpdatePage } from './scheduler.page-object';

const expect = chai.expect;

describe('Scheduler e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let schedulerUpdatePage: SchedulerUpdatePage;
    let schedulerComponentsPage: SchedulerComponentsPage;
    let schedulerDeleteDialog: SchedulerDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Schedulers', async () => {
        await navBarPage.goToEntity('scheduler');
        schedulerComponentsPage = new SchedulerComponentsPage();
        expect(await schedulerComponentsPage.getTitle()).to.eq('riscScraperAdmApp.scheduler.home.title');
    });

    it('should load create Scheduler page', async () => {
        await schedulerComponentsPage.clickOnCreateButton();
        schedulerUpdatePage = new SchedulerUpdatePage();
        expect(await schedulerUpdatePage.getPageTitle()).to.eq('riscScraperAdmApp.scheduler.home.createOrEditLabel');
        await schedulerUpdatePage.cancel();
    });

    it('should create and save Schedulers', async () => {
        const nbButtonsBeforeCreate = await schedulerComponentsPage.countDeleteButtons();

        await schedulerComponentsPage.clickOnCreateButton();
        await promise.all([
            schedulerUpdatePage.setIdentifierInput('identifier'),
            schedulerUpdatePage.setStatusInput('5'),
            schedulerUpdatePage.setActorInput('5'),
            schedulerUpdatePage.setScheduleInput('schedule'),
            schedulerUpdatePage.setProjectInput('project'),
            schedulerUpdatePage.setSpiderInput('spider'),
            schedulerUpdatePage.setTimestampInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            schedulerUpdatePage.setDurationInput('5'),
            schedulerUpdatePage.setJobidentifierInput('jobidentifier')
        ]);
        expect(await schedulerUpdatePage.getIdentifierInput()).to.eq('identifier');
        expect(await schedulerUpdatePage.getStatusInput()).to.eq('5');
        expect(await schedulerUpdatePage.getActorInput()).to.eq('5');
        expect(await schedulerUpdatePage.getScheduleInput()).to.eq('schedule');
        expect(await schedulerUpdatePage.getProjectInput()).to.eq('project');
        expect(await schedulerUpdatePage.getSpiderInput()).to.eq('spider');
        expect(await schedulerUpdatePage.getTimestampInput()).to.contain('2001-01-01T02:30');
        expect(await schedulerUpdatePage.getDurationInput()).to.eq('5');
        expect(await schedulerUpdatePage.getJobidentifierInput()).to.eq('jobidentifier');
        await schedulerUpdatePage.save();
        expect(await schedulerUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await schedulerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Scheduler', async () => {
        const nbButtonsBeforeDelete = await schedulerComponentsPage.countDeleteButtons();
        await schedulerComponentsPage.clickOnLastDeleteButton();

        schedulerDeleteDialog = new SchedulerDeleteDialog();
        expect(await schedulerDeleteDialog.getDialogTitle()).to.eq('riscScraperAdmApp.scheduler.delete.question');
        await schedulerDeleteDialog.clickOnConfirmButton();

        expect(await schedulerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
