import { element, by, ElementFinder } from 'protractor';

export class PortalComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-portal div table .btn-danger'));
    title = element.all(by.css('jhi-portal div h2#page-heading span')).first();

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

export class PortalUpdatePage {
    pageTitle = element(by.id('jhi-portal-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    idscrapyDataInput = element(by.id('field_idscrapyData'));
    nameInput = element(by.id('field_name'));
    domainInput = element(by.id('field_domain'));
    urlInput = element(by.id('field_url'));
    headerPathInput = element(by.id('field_headerPath'));
    bodyPathInput = element(by.id('field_bodyPath'));
    autorPathInput = element(by.id('field_autorPath'));
    datePathInput = element(by.id('field_datePath'));
    resumePathInput = element(by.id('field_resumePath'));
    pathInput = element(by.id('field_path'));
    xpathInput = element(by.id('field_xpath'));
    identifierInput = element(by.id('field_identifier'));
    usuarioInput = element(by.id('field_usuario'));
    fechaInput = element(by.id('field_fecha'));
    schedulerSelect = element(by.id('field_scheduler'));
    portalTypeSelect = element(by.id('field_portalType'));
    customerSelect = element(by.id('field_customer'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setIdscrapyDataInput(idscrapyData) {
        await this.idscrapyDataInput.sendKeys(idscrapyData);
    }

    async getIdscrapyDataInput() {
        return this.idscrapyDataInput.getAttribute('value');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setDomainInput(domain) {
        await this.domainInput.sendKeys(domain);
    }

    async getDomainInput() {
        return this.domainInput.getAttribute('value');
    }

    async setUrlInput(url) {
        await this.urlInput.sendKeys(url);
    }

    async getUrlInput() {
        return this.urlInput.getAttribute('value');
    }

    async setHeaderPathInput(headerPath) {
        await this.headerPathInput.sendKeys(headerPath);
    }

    async getHeaderPathInput() {
        return this.headerPathInput.getAttribute('value');
    }

    async setBodyPathInput(bodyPath) {
        await this.bodyPathInput.sendKeys(bodyPath);
    }

    async getBodyPathInput() {
        return this.bodyPathInput.getAttribute('value');
    }

    async setAutorPathInput(autorPath) {
        await this.autorPathInput.sendKeys(autorPath);
    }

    async getAutorPathInput() {
        return this.autorPathInput.getAttribute('value');
    }

    async setDatePathInput(datePath) {
        await this.datePathInput.sendKeys(datePath);
    }

    async getDatePathInput() {
        return this.datePathInput.getAttribute('value');
    }

    async setResumePathInput(resumePath) {
        await this.resumePathInput.sendKeys(resumePath);
    }

    async getResumePathInput() {
        return this.resumePathInput.getAttribute('value');
    }

    async setPathInput(path) {
        await this.pathInput.sendKeys(path);
    }

    async getPathInput() {
        return this.pathInput.getAttribute('value');
    }

    getXpathInput() {
        return this.xpathInput;
    }
    async setIdentifierInput(identifier) {
        await this.identifierInput.sendKeys(identifier);
    }

    async getIdentifierInput() {
        return this.identifierInput.getAttribute('value');
    }

    async setUsuarioInput(usuario) {
        await this.usuarioInput.sendKeys(usuario);
    }

    async getUsuarioInput() {
        return this.usuarioInput.getAttribute('value');
    }

    async setFechaInput(fecha) {
        await this.fechaInput.sendKeys(fecha);
    }

    async getFechaInput() {
        return this.fechaInput.getAttribute('value');
    }

    async schedulerSelectLastOption() {
        await this.schedulerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async schedulerSelectOption(option) {
        await this.schedulerSelect.sendKeys(option);
    }

    getSchedulerSelect(): ElementFinder {
        return this.schedulerSelect;
    }

    async getSchedulerSelectedOption() {
        return this.schedulerSelect.element(by.css('option:checked')).getText();
    }

    async portalTypeSelectLastOption() {
        await this.portalTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async portalTypeSelectOption(option) {
        await this.portalTypeSelect.sendKeys(option);
    }

    getPortalTypeSelect(): ElementFinder {
        return this.portalTypeSelect;
    }

    async getPortalTypeSelectedOption() {
        return this.portalTypeSelect.element(by.css('option:checked')).getText();
    }

    async customerSelectLastOption() {
        await this.customerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async customerSelectOption(option) {
        await this.customerSelect.sendKeys(option);
    }

    getCustomerSelect(): ElementFinder {
        return this.customerSelect;
    }

    async getCustomerSelectedOption() {
        return this.customerSelect.element(by.css('option:checked')).getText();
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

export class PortalDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-portal-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-portal'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
