import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AdventureRaceOptionsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-adventure-race-options div table .btn-danger'));
  title = element.all(by.css('jhi-adventure-race-options div h2#page-heading span')).first();

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

export class AdventureRaceOptionsUpdatePage {
  pageTitle = element(by.id('jhi-adventure-race-options-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  heightInput = element(by.id('field_height'));
  weightInput = element(by.id('field_weight'));
  adventureModelOptionsSelect = element(by.id('field_adventureModelOptions'));
  adventureModelSelect = element(by.id('field_adventureModel'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setHeightInput(height) {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput() {
    return await this.heightInput.getAttribute('value');
  }

  async setWeightInput(weight) {
    await this.weightInput.sendKeys(weight);
  }

  async getWeightInput() {
    return await this.weightInput.getAttribute('value');
  }

  async adventureModelOptionsSelectLastOption(timeout?: number) {
    await this.adventureModelOptionsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureModelOptionsSelectOption(option) {
    await this.adventureModelOptionsSelect.sendKeys(option);
  }

  getAdventureModelOptionsSelect(): ElementFinder {
    return this.adventureModelOptionsSelect;
  }

  async getAdventureModelOptionsSelectedOption() {
    return await this.adventureModelOptionsSelect.element(by.css('option:checked')).getText();
  }

  async adventureModelSelectLastOption(timeout?: number) {
    await this.adventureModelSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureModelSelectOption(option) {
    await this.adventureModelSelect.sendKeys(option);
  }

  getAdventureModelSelect(): ElementFinder {
    return this.adventureModelSelect;
  }

  async getAdventureModelSelectedOption() {
    return await this.adventureModelSelect.element(by.css('option:checked')).getText();
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

export class AdventureRaceOptionsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-adventureRaceOptions-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-adventureRaceOptions'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
