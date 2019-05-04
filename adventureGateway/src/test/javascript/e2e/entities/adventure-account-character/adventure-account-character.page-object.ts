import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AdventureAccountCharacterComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-adventure-account-character div table .btn-danger'));
  title = element.all(by.css('jhi-adventure-account-character div h2#page-heading span')).first();

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

export class AdventureAccountCharacterUpdatePage {
  pageTitle = element(by.id('jhi-adventure-account-character-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nicknameInput = element(by.id('field_nickname'));
  genderInput = element(by.id('field_gender'));
  adventureInventoryCharSelect = element(by.id('field_adventureInventoryChar'));
  adventureCharacteristicSelect = element(by.id('field_adventureCharacteristic'));
  adventureRaceSelect = element(by.id('field_adventureRace'));
  adventureSkillSelect = element(by.id('field_adventureSkill'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNicknameInput(nickname) {
    await this.nicknameInput.sendKeys(nickname);
  }

  async getNicknameInput() {
    return await this.nicknameInput.getAttribute('value');
  }

  getGenderInput(timeout?: number) {
    return this.genderInput;
  }

  async adventureInventoryCharSelectLastOption(timeout?: number) {
    await this.adventureInventoryCharSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureInventoryCharSelectOption(option) {
    await this.adventureInventoryCharSelect.sendKeys(option);
  }

  getAdventureInventoryCharSelect(): ElementFinder {
    return this.adventureInventoryCharSelect;
  }

  async getAdventureInventoryCharSelectedOption() {
    return await this.adventureInventoryCharSelect.element(by.css('option:checked')).getText();
  }

  async adventureCharacteristicSelectLastOption(timeout?: number) {
    await this.adventureCharacteristicSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureCharacteristicSelectOption(option) {
    await this.adventureCharacteristicSelect.sendKeys(option);
  }

  getAdventureCharacteristicSelect(): ElementFinder {
    return this.adventureCharacteristicSelect;
  }

  async getAdventureCharacteristicSelectedOption() {
    return await this.adventureCharacteristicSelect.element(by.css('option:checked')).getText();
  }

  async adventureRaceSelectLastOption(timeout?: number) {
    await this.adventureRaceSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureRaceSelectOption(option) {
    await this.adventureRaceSelect.sendKeys(option);
  }

  getAdventureRaceSelect(): ElementFinder {
    return this.adventureRaceSelect;
  }

  async getAdventureRaceSelectedOption() {
    return await this.adventureRaceSelect.element(by.css('option:checked')).getText();
  }

  async adventureSkillSelectLastOption(timeout?: number) {
    await this.adventureSkillSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureSkillSelectOption(option) {
    await this.adventureSkillSelect.sendKeys(option);
  }

  getAdventureSkillSelect(): ElementFinder {
    return this.adventureSkillSelect;
  }

  async getAdventureSkillSelectedOption() {
    return await this.adventureSkillSelect.element(by.css('option:checked')).getText();
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

export class AdventureAccountCharacterDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-adventureAccountCharacter-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-adventureAccountCharacter'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
