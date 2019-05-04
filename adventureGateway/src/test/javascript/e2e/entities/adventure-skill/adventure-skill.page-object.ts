import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AdventureSkillComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-adventure-skill div table .btn-danger'));
  title = element.all(by.css('jhi-adventure-skill div h2#page-heading span')).first();

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

export class AdventureSkillUpdatePage {
  pageTitle = element(by.id('jhi-adventure-skill-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  positionInput = element(by.id('field_position'));
  adventureScriptSelect = element(by.id('field_adventureScript'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  getPositionInput(timeout?: number) {
    return this.positionInput;
  }

  async adventureScriptSelectLastOption(timeout?: number) {
    await this.adventureScriptSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureScriptSelectOption(option) {
    await this.adventureScriptSelect.sendKeys(option);
  }

  getAdventureScriptSelect(): ElementFinder {
    return this.adventureScriptSelect;
  }

  async getAdventureScriptSelectedOption() {
    return await this.adventureScriptSelect.element(by.css('option:checked')).getText();
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

export class AdventureSkillDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-adventureSkill-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-adventureSkill'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
