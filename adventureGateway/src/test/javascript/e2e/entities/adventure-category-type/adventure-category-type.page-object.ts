import { element, by, ElementFinder } from 'protractor';

export class AdventureCategoryTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-adventure-category-type div table .btn-danger'));
    title = element.all(by.css('jhi-adventure-category-type div h2#page-heading span')).first();

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

export class AdventureCategoryTypeUpdatePage {
    pageTitle = element(by.id('jhi-adventure-category-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    descInput = element(by.id('field_desc'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setDescInput(desc) {
        await this.descInput.sendKeys(desc);
    }

    async getDescInput() {
        return this.descInput.getAttribute('value');
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

export class AdventureCategoryTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-adventureCategoryType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-adventureCategoryType'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
