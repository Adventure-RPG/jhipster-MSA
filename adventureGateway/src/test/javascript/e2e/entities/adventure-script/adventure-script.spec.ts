/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AdventureScriptComponentsPage, AdventureScriptDeleteDialog, AdventureScriptUpdatePage } from './adventure-script.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AdventureScript e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let adventureScriptUpdatePage: AdventureScriptUpdatePage;
  let adventureScriptComponentsPage: AdventureScriptComponentsPage;
  let adventureScriptDeleteDialog: AdventureScriptDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AdventureScripts', async () => {
    await navBarPage.goToEntity('adventure-script');
    adventureScriptComponentsPage = new AdventureScriptComponentsPage();
    await browser.wait(ec.visibilityOf(adventureScriptComponentsPage.title), 5000);
    expect(await adventureScriptComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureScript.home.title');
  });

  it('should load create AdventureScript page', async () => {
    await adventureScriptComponentsPage.clickOnCreateButton();
    adventureScriptUpdatePage = new AdventureScriptUpdatePage();
    expect(await adventureScriptUpdatePage.getPageTitle()).to.eq('adventureGatewayApp.adventureScript.home.createOrEditLabel');
    await adventureScriptUpdatePage.cancel();
  });

  it('should create and save AdventureScripts', async () => {
    const nbButtonsBeforeCreate = await adventureScriptComponentsPage.countDeleteButtons();

    await adventureScriptComponentsPage.clickOnCreateButton();
    await promise.all([
      adventureScriptUpdatePage.setNameInput('name'),
      adventureScriptUpdatePage.setFileInput(absolutePath),
      adventureScriptUpdatePage.setArgumentsScriptInput('argumentsScript')
    ]);
    expect(await adventureScriptUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await adventureScriptUpdatePage.getFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected File value to be end with ' + fileNameToUpload
    );
    expect(await adventureScriptUpdatePage.getArgumentsScriptInput()).to.eq(
      'argumentsScript',
      'Expected ArgumentsScript value to be equals to argumentsScript'
    );
    await adventureScriptUpdatePage.save();
    expect(await adventureScriptUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await adventureScriptComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AdventureScript', async () => {
    const nbButtonsBeforeDelete = await adventureScriptComponentsPage.countDeleteButtons();
    await adventureScriptComponentsPage.clickOnLastDeleteButton();

    adventureScriptDeleteDialog = new AdventureScriptDeleteDialog();
    expect(await adventureScriptDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureScript.delete.question');
    await adventureScriptDeleteDialog.clickOnConfirmButton();

    expect(await adventureScriptComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
