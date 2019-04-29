import { element, by, ElementFinder } from 'protractor';

export class AdventureItemComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-adventure-item div table .btn-danger'));
    title = element.all(by.css('jhi-adventure-item div h2#page-heading span')).first();

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

export class AdventureItemUpdatePage {
    pageTitle = element(by.id('jhi-adventure-item-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    isEquipmentInput = element(by.id('field_isEquipment'));
    equipmentSlotSelect = element(by.id('field_equipmentSlot'));
    priceInput = element(by.id('field_price'));
    weightInput = element(by.id('field_weight'));
    adventureAttributesSelect = element(by.id('field_adventureAttributes'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    getIsEquipmentInput() {
        return this.isEquipmentInput;
    }
    async setEquipmentSlotSelect(equipmentSlot) {
        await this.equipmentSlotSelect.sendKeys(equipmentSlot);
    }

    async getEquipmentSlotSelect() {
        return this.equipmentSlotSelect.element(by.css('option:checked')).getText();
    }

    async equipmentSlotSelectLastOption() {
        await this.equipmentSlotSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async setWeightInput(weight) {
        await this.weightInput.sendKeys(weight);
    }

    async getWeightInput() {
        return this.weightInput.getAttribute('value');
    }

    async adventureAttributesSelectLastOption() {
        await this.adventureAttributesSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async adventureAttributesSelectOption(option) {
        await this.adventureAttributesSelect.sendKeys(option);
    }

    getAdventureAttributesSelect(): ElementFinder {
        return this.adventureAttributesSelect;
    }

    async getAdventureAttributesSelectedOption() {
        return this.adventureAttributesSelect.element(by.css('option:checked')).getText();
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

export class AdventureItemDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-adventureItem-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-adventureItem'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
