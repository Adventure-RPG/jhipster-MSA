import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AdventureAttributesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-adventure-attributes div table .btn-danger'));
  title = element.all(by.css('jhi-adventure-attributes div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
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
    return await this.defenceInput.getAttribute('value');
  }

  async setDefenceArmorTypeSelect(defenceArmorType) {
    await this.defenceArmorTypeSelect.sendKeys(defenceArmorType);
  }

  async getDefenceArmorTypeSelect() {
    return await this.defenceArmorTypeSelect.element(by.css('option:checked')).getText();
  }

  async defenceArmorTypeSelectLastOption(timeout?: number) {
    await this.defenceArmorTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setFireResistanceInput(fireResistance) {
    await this.fireResistanceInput.sendKeys(fireResistance);
  }

  async getFireResistanceInput() {
    return await this.fireResistanceInput.getAttribute('value');
  }

  async setEarthResistanceInput(earthResistance) {
    await this.earthResistanceInput.sendKeys(earthResistance);
  }

  async getEarthResistanceInput() {
    return await this.earthResistanceInput.getAttribute('value');
  }

  async setWaterResistanceInput(waterResistance) {
    await this.waterResistanceInput.sendKeys(waterResistance);
  }

  async getWaterResistanceInput() {
    return await this.waterResistanceInput.getAttribute('value');
  }

  async setWindResistanceInput(windResistance) {
    await this.windResistanceInput.sendKeys(windResistance);
  }

  async getWindResistanceInput() {
    return await this.windResistanceInput.getAttribute('value');
  }

  async setActiveWeaponAttackDamageSelect(activeWeaponAttackDamage) {
    await this.activeWeaponAttackDamageSelect.sendKeys(activeWeaponAttackDamage);
  }

  async getActiveWeaponAttackDamageSelect() {
    return await this.activeWeaponAttackDamageSelect.element(by.css('option:checked')).getText();
  }

  async activeWeaponAttackDamageSelectLastOption(timeout?: number) {
    await this.activeWeaponAttackDamageSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setActiveWeaponAttackHitInput(activeWeaponAttackHit) {
    await this.activeWeaponAttackHitInput.sendKeys(activeWeaponAttackHit);
  }

  async getActiveWeaponAttackHitInput() {
    return await this.activeWeaponAttackHitInput.getAttribute('value');
  }

  async setActiveWeaponAttackTypeInput(activeWeaponAttackType) {
    await this.activeWeaponAttackTypeInput.sendKeys(activeWeaponAttackType);
  }

  async getActiveWeaponAttackTypeInput() {
    return await this.activeWeaponAttackTypeInput.getAttribute('value');
  }

  async setSizeInput(size) {
    await this.sizeInput.sendKeys(size);
  }

  async getSizeInput() {
    return await this.sizeInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
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

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
