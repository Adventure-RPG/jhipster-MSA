import { element, by, ElementFinder } from 'protractor';

export class AdventureModelOptionsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-adventure-model-options div table .btn-danger'));
    title = element.all(by.css('jhi-adventure-model-options div h2#page-heading span')).first();

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

export class AdventureModelOptionsUpdatePage {
    pageTitle = element(by.id('jhi-adventure-model-options-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    colorInput = element(by.id('field_color'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setColorInput(color) {
        await this.colorInput.sendKeys(color);
    }

    async getColorInput() {
        return this.colorInput.getAttribute('value');
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

export class AdventureModelOptionsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-adventureModelOptions-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-adventureModelOptions'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
