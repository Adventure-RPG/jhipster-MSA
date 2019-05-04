import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AdventureInventoryCharComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-adventure-inventory-char div table .btn-danger'));
  title = element.all(by.css('jhi-adventure-inventory-char div h2#page-heading span')).first();

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

export class AdventureInventoryCharUpdatePage {
  pageTitle = element(by.id('jhi-adventure-inventory-char-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  adventureItemSelect = element(by.id('field_adventureItem'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async adventureItemSelectLastOption(timeout?: number) {
    await this.adventureItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adventureItemSelectOption(option) {
    await this.adventureItemSelect.sendKeys(option);
  }

  getAdventureItemSelect(): ElementFinder {
    return this.adventureItemSelect;
  }

  async getAdventureItemSelectedOption() {
    return await this.adventureItemSelect.element(by.css('option:checked')).getText();
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

export class AdventureInventoryCharDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-adventureInventoryChar-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-adventureInventoryChar'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
