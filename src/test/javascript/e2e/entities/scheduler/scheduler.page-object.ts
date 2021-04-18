import { element, by, ElementFinder } from 'protractor';

export class SchedulerComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-scheduler div table .btn-danger'));
    title = element.all(by.css('jhi-scheduler div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SchedulerUpdatePage {
    pageTitle = element(by.id('jhi-scheduler-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    identifierInput = element(by.id('field_identifier'));
    statusInput = element(by.id('field_status'));
    actorInput = element(by.id('field_actor'));
    scheduleInput = element(by.id('field_schedule'));
    projectInput = element(by.id('field_project'));
    spiderInput = element(by.id('field_spider'));
    timestampInput = element(by.id('field_timestamp'));
    durationInput = element(by.id('field_duration'));
    jobidentifierInput = element(by.id('field_jobidentifier'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setIdentifierInput(identifier) {
        await this.identifierInput.sendKeys(identifier);
    }

    async getIdentifierInput() {
        return this.identifierInput.getAttribute('value');
    }

    async setStatusInput(status) {
        await this.statusInput.sendKeys(status);
    }

    async getStatusInput() {
        return this.statusInput.getAttribute('value');
    }

    async setActorInput(actor) {
        await this.actorInput.sendKeys(actor);
    }

    async getActorInput() {
        return this.actorInput.getAttribute('value');
    }

    async setScheduleInput(schedule) {
        await this.scheduleInput.sendKeys(schedule);
    }

    async getScheduleInput() {
        return this.scheduleInput.getAttribute('value');
    }

    async setProjectInput(project) {
        await this.projectInput.sendKeys(project);
    }

    async getProjectInput() {
        return this.projectInput.getAttribute('value');
    }

    async setSpiderInput(spider) {
        await this.spiderInput.sendKeys(spider);
    }

    async getSpiderInput() {
        return this.spiderInput.getAttribute('value');
    }

    async setTimestampInput(timestamp) {
        await this.timestampInput.sendKeys(timestamp);
    }

    async getTimestampInput() {
        return this.timestampInput.getAttribute('value');
    }

    async setDurationInput(duration) {
        await this.durationInput.sendKeys(duration);
    }

    async getDurationInput() {
        return this.durationInput.getAttribute('value');
    }

    async setJobidentifierInput(jobidentifier) {
        await this.jobidentifierInput.sendKeys(jobidentifier);
    }

    async getJobidentifierInput() {
        return this.jobidentifierInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class SchedulerDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-scheduler-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-scheduler'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
