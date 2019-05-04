import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AdventureRaceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-adventure-race div table .btn-danger'));
  title = element.all(by.css('jhi-adventure-race div h2#page-heading span')).first();

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

export class AdventureRaceUpdatePage {
  pageTitle = element(by.id('jhi-adventure-race-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  descInput = element(by.id('field_desc'));
  imageInput = element(by.id('file_image'));
  adventureRaceOptionsSelect = element(by.id('field_adventureRaceOptions'));
  adventureFractionSelect = element(by.id('field_adventureFraction'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setDescInput(desc) {
    await this.descInput.sendKeys(desc);
  }

  async getDescInput() {
    return await this.descInput.getAttribute('value');
  }

  async setImageInput(image) {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput() {
    return await this.imageInput.getAttribute('value');
  }

  async adventureRaceOptionsSelectLastOption(timeout?: number) {
    await this.adventureRaceOptionsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureRaceOptionsSelectOption(option) {
    await this.adventureRaceOptionsSelect.sendKeys(option);
  }

  getAdventureRaceOptionsSelect(): ElementFinder {
    return this.adventureRaceOptionsSelect;
  }

  async getAdventureRaceOptionsSelectedOption() {
    return await this.adventureRaceOptionsSelect.element(by.css('option:checked')).getText();
  }

  async adventureFractionSelectLastOption(timeout?: number) {
    await this.adventureFractionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureFractionSelectOption(option) {
    await this.adventureFractionSelect.sendKeys(option);
  }

  getAdventureFractionSelect(): ElementFinder {
    return this.adventureFractionSelect;
  }

  async getAdventureFractionSelectedOption() {
    return await this.adventureFractionSelect.element(by.css('option:checked')).getText();
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

export class AdventureRaceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-adventureRace-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-adventureRace'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
