import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AdventureScriptComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-adventure-script div table .btn-danger'));
  title = element.all(by.css('jhi-adventure-script div h2#page-heading span')).first();

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

export class AdventureScriptUpdatePage {
  pageTitle = element(by.id('jhi-adventure-script-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  fileInput = element(by.id('file_file'));
  argumentsScriptInput = element(by.id('field_argumentsScript'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setFileInput(file) {
    await this.fileInput.sendKeys(file);
  }

  async getFileInput() {
    return await this.fileInput.getAttribute('value');
  }

  async setArgumentsScriptInput(argumentsScript) {
    await this.argumentsScriptInput.sendKeys(argumentsScript);
  }

  async getArgumentsScriptInput() {
    return await this.argumentsScriptInput.getAttribute('value');
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

export class AdventureScriptDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-adventureScript-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-adventureScript'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
