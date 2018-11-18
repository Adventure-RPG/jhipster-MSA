import { element, by, ElementFinder } from 'protractor';

export class AdventureModelComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-adventure-model div table .btn-danger'));
    title = element.all(by.css('jhi-adventure-model div h2#page-heading span')).first();

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

export class AdventureModelUpdatePage {
    pageTitle = element(by.id('jhi-adventure-model-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    fileInput = element(by.id('file_file'));
    adventureCategoryTypeSelect = element(by.id('field_adventureCategoryType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setFileInput(file) {
        await this.fileInput.sendKeys(file);
    }

    async getFileInput() {
        return this.fileInput.getAttribute('value');
    }

    async adventureCategoryTypeSelectLastOption() {
        await this.adventureCategoryTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async adventureCategoryTypeSelectOption(option) {
        await this.adventureCategoryTypeSelect.sendKeys(option);
    }

    getAdventureCategoryTypeSelect(): ElementFinder {
        return this.adventureCategoryTypeSelect;
    }

    async getAdventureCategoryTypeSelectedOption() {
        return this.adventureCategoryTypeSelect.element(by.css('option:checked')).getText();
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

export class AdventureModelDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-adventureModel-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-adventureModel'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
