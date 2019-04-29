import { element, by, ElementFinder } from 'protractor';

export class AdventureCharacteristicComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-adventure-characteristic div table .btn-danger'));
    title = element.all(by.css('jhi-adventure-characteristic div h2#page-heading span')).first();

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

export class AdventureCharacteristicUpdatePage {
    pageTitle = element(by.id('jhi-adventure-characteristic-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    strengthInput = element(by.id('field_strength'));
    agilityInput = element(by.id('field_agility'));
    vitalityInput = element(by.id('field_vitality'));
    luckInput = element(by.id('field_luck'));
    intelligenceInput = element(by.id('field_intelligence'));
    wisdomInput = element(by.id('field_wisdom'));
    charismaInput = element(by.id('field_charisma'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setStrengthInput(strength) {
        await this.strengthInput.sendKeys(strength);
    }

    async getStrengthInput() {
        return this.strengthInput.getAttribute('value');
    }

    async setAgilityInput(agility) {
        await this.agilityInput.sendKeys(agility);
    }

    async getAgilityInput() {
        return this.agilityInput.getAttribute('value');
    }

    async setVitalityInput(vitality) {
        await this.vitalityInput.sendKeys(vitality);
    }

    async getVitalityInput() {
        return this.vitalityInput.getAttribute('value');
    }

    async setLuckInput(luck) {
        await this.luckInput.sendKeys(luck);
    }

    async getLuckInput() {
        return this.luckInput.getAttribute('value');
    }

    async setIntelligenceInput(intelligence) {
        await this.intelligenceInput.sendKeys(intelligence);
    }

    async getIntelligenceInput() {
        return this.intelligenceInput.getAttribute('value');
    }

    async setWisdomInput(wisdom) {
        await this.wisdomInput.sendKeys(wisdom);
    }

    async getWisdomInput() {
        return this.wisdomInput.getAttribute('value');
    }

    async setCharismaInput(charisma) {
        await this.charismaInput.sendKeys(charisma);
    }

    async getCharismaInput() {
        return this.charismaInput.getAttribute('value');
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

export class AdventureCharacteristicDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-adventureCharacteristic-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-adventureCharacteristic'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
