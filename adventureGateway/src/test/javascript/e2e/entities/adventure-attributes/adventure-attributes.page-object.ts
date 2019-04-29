import { element, by, ElementFinder } from 'protractor';

export class AdventureAttributesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-adventure-attributes div table .btn-danger'));
    title = element.all(by.css('jhi-adventure-attributes div h2#page-heading span')).first();

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

export class AdventureAttributesUpdatePage {
    pageTitle = element(by.id('jhi-adventure-attributes-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    defenceInput = element(by.id('field_defence'));
    defenceArmorTypeSelect = element(by.id('field_defenceArmorType'));
    fireResistanceInput = element(by.id('field_fireResistance'));
    earthResistanceInput = element(by.id('field_earthResistance'));
    waterResistanceInput = element(by.id('field_waterResistance'));
    windResistanceInput = element(by.id('field_windResistance'));
    activeWeaponAttackDamageSelect = element(by.id('field_activeWeaponAttackDamage'));
    activeWeaponAttackHitInput = element(by.id('field_activeWeaponAttackHit'));
    activeWeaponAttackTypeInput = element(by.id('field_activeWeaponAttackType'));
    sizeInput = element(by.id('field_size'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDefenceInput(defence) {
        await this.defenceInput.sendKeys(defence);
    }

    async getDefenceInput() {
        return this.defenceInput.getAttribute('value');
    }

    async setDefenceArmorTypeSelect(defenceArmorType) {
        await this.defenceArmorTypeSelect.sendKeys(defenceArmorType);
    }

    async getDefenceArmorTypeSelect() {
        return this.defenceArmorTypeSelect.element(by.css('option:checked')).getText();
    }

    async defenceArmorTypeSelectLastOption() {
        await this.defenceArmorTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setFireResistanceInput(fireResistance) {
        await this.fireResistanceInput.sendKeys(fireResistance);
    }

    async getFireResistanceInput() {
        return this.fireResistanceInput.getAttribute('value');
    }

    async setEarthResistanceInput(earthResistance) {
        await this.earthResistanceInput.sendKeys(earthResistance);
    }

    async getEarthResistanceInput() {
        return this.earthResistanceInput.getAttribute('value');
    }

    async setWaterResistanceInput(waterResistance) {
        await this.waterResistanceInput.sendKeys(waterResistance);
    }

    async getWaterResistanceInput() {
        return this.waterResistanceInput.getAttribute('value');
    }

    async setWindResistanceInput(windResistance) {
        await this.windResistanceInput.sendKeys(windResistance);
    }

    async getWindResistanceInput() {
        return this.windResistanceInput.getAttribute('value');
    }

    async setActiveWeaponAttackDamageSelect(activeWeaponAttackDamage) {
        await this.activeWeaponAttackDamageSelect.sendKeys(activeWeaponAttackDamage);
    }

    async getActiveWeaponAttackDamageSelect() {
        return this.activeWeaponAttackDamageSelect.element(by.css('option:checked')).getText();
    }

    async activeWeaponAttackDamageSelectLastOption() {
        await this.activeWeaponAttackDamageSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setActiveWeaponAttackHitInput(activeWeaponAttackHit) {
        await this.activeWeaponAttackHitInput.sendKeys(activeWeaponAttackHit);
    }

    async getActiveWeaponAttackHitInput() {
        return this.activeWeaponAttackHitInput.getAttribute('value');
    }

    async setActiveWeaponAttackTypeInput(activeWeaponAttackType) {
        await this.activeWeaponAttackTypeInput.sendKeys(activeWeaponAttackType);
    }

    async getActiveWeaponAttackTypeInput() {
        return this.activeWeaponAttackTypeInput.getAttribute('value');
    }

    async setSizeInput(size) {
        await this.sizeInput.sendKeys(size);
    }

    async getSizeInput() {
        return this.sizeInput.getAttribute('value');
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

export class AdventureAttributesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-adventureAttributes-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-adventureAttributes'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
