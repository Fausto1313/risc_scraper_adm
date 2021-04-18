import { element, by, ElementFinder } from 'protractor';

export class PagesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-pages div table .btn-danger'));
    title = element.all(by.css('jhi-pages div h2#page-heading span')).first();

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

export class PagesUpdatePage {
    pageTitle = element(by.id('jhi-pages-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    idscrapyDataInput = element(by.id('field_idscrapyData'));
    headerDataInput = element(by.id('field_headerData'));
    bodyDataInput = element(by.id('field_bodyData'));
    autorDataInput = element(by.id('field_autorData'));
    dateDataInput = element(by.id('field_dateData'));
    headerCleanInput = element(by.id('field_headerClean'));
    bodyCleanInput = element(by.id('field_bodyClean'));
    autorCleanInput = element(by.id('field_autorClean'));
    dateCleanInput = element(by.id('field_dateClean'));
    resumeCleanInput = element(by.id('field_resumeClean'));
    resumeDataInput = element(by.id('field_resumeData'));
    screenshotInput = element(by.id('file_screenshot'));
    dateInput = element(by.id('field_date'));
    fullPathInput = element(by.id('field_fullPath'));
    urlInput = element(by.id('field_url'));
    portalSelect = element(by.id('field_portal'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setIdscrapyDataInput(idscrapyData) {
        await this.idscrapyDataInput.sendKeys(idscrapyData);
    }

    async getIdscrapyDataInput() {
        return this.idscrapyDataInput.getAttribute('value');
    }

    async setHeaderDataInput(headerData) {
        await this.headerDataInput.sendKeys(headerData);
    }

    async getHeaderDataInput() {
        return this.headerDataInput.getAttribute('value');
    }

    async setBodyDataInput(bodyData) {
        await this.bodyDataInput.sendKeys(bodyData);
    }

    async getBodyDataInput() {
        return this.bodyDataInput.getAttribute('value');
    }

    async setAutorDataInput(autorData) {
        await this.autorDataInput.sendKeys(autorData);
    }

    async getAutorDataInput() {
        return this.autorDataInput.getAttribute('value');
    }

    async setDateDataInput(dateData) {
        await this.dateDataInput.sendKeys(dateData);
    }

    async getDateDataInput() {
        return this.dateDataInput.getAttribute('value');
    }

    async setHeaderCleanInput(headerClean) {
        await this.headerCleanInput.sendKeys(headerClean);
    }

    async getHeaderCleanInput() {
        return this.headerCleanInput.getAttribute('value');
    }

    async setBodyCleanInput(bodyClean) {
        await this.bodyCleanInput.sendKeys(bodyClean);
    }

    async getBodyCleanInput() {
        return this.bodyCleanInput.getAttribute('value');
    }

    async setAutorCleanInput(autorClean) {
        await this.autorCleanInput.sendKeys(autorClean);
    }

    async getAutorCleanInput() {
        return this.autorCleanInput.getAttribute('value');
    }

    async setDateCleanInput(dateClean) {
        await this.dateCleanInput.sendKeys(dateClean);
    }

    async getDateCleanInput() {
        return this.dateCleanInput.getAttribute('value');
    }

    async setResumeCleanInput(resumeClean) {
        await this.resumeCleanInput.sendKeys(resumeClean);
    }

    async getResumeCleanInput() {
        return this.resumeCleanInput.getAttribute('value');
    }

    async setResumeDataInput(resumeData) {
        await this.resumeDataInput.sendKeys(resumeData);
    }

    async getResumeDataInput() {
        return this.resumeDataInput.getAttribute('value');
    }

    async setScreenshotInput(screenshot) {
        await this.screenshotInput.sendKeys(screenshot);
    }

    async getScreenshotInput() {
        return this.screenshotInput.getAttribute('value');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setFullPathInput(fullPath) {
        await this.fullPathInput.sendKeys(fullPath);
    }

    async getFullPathInput() {
        return this.fullPathInput.getAttribute('value');
    }

    async setUrlInput(url) {
        await this.urlInput.sendKeys(url);
    }

    async getUrlInput() {
        return this.urlInput.getAttribute('value');
    }

    async portalSelectLastOption() {
        await this.portalSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async portalSelectOption(option) {
        await this.portalSelect.sendKeys(option);
    }

    getPortalSelect(): ElementFinder {
        return this.portalSelect;
    }

    async getPortalSelectedOption() {
        return this.portalSelect.element(by.css('option:checked')).getText();
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

export class PagesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-pages-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-pages'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
