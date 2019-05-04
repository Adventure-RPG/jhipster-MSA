/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AdventureModelComponentsPage, AdventureModelDeleteDialog, AdventureModelUpdatePage } from './adventure-model.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AdventureModel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let adventureModelUpdatePage: AdventureModelUpdatePage;
  let adventureModelComponentsPage: AdventureModelComponentsPage;
  let adventureModelDeleteDialog: AdventureModelDeleteDialog;
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

  it('should load AdventureModels', async () => {
    await navBarPage.goToEntity('adventure-model');
    adventureModelComponentsPage = new AdventureModelComponentsPage();
    await browser.wait(ec.visibilityOf(adventureModelComponentsPage.title), 5000);
    expect(await adventureModelComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureModel.home.title');
  });

  it('should load create AdventureModel page', async () => {
    await adventureModelComponentsPage.clickOnCreateButton();
    adventureModelUpdatePage = new AdventureModelUpdatePage();
    expect(await adventureModelUpdatePage.getPageTitle()).to.eq('adventureGatewayApp.adventureModel.home.createOrEditLabel');
    await adventureModelUpdatePage.cancel();
  });

  it('should create and save AdventureModels', async () => {
    const nbButtonsBeforeCreate = await adventureModelComponentsPage.countDeleteButtons();

    await adventureModelComponentsPage.clickOnCreateButton();
    await promise.all([
      adventureModelUpdatePage.setNameInput('name'),
      adventureModelUpdatePage.setFileInput(absolutePath),
      adventureModelUpdatePage.adventureCategoryTypeSelectLastOption()
    ]);
    expect(await adventureModelUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await adventureModelUpdatePage.getFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected File value to be end with ' + fileNameToUpload
    );
    await adventureModelUpdatePage.save();
    expect(await adventureModelUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await adventureModelComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AdventureModel', async () => {
    const nbButtonsBeforeDelete = await adventureModelComponentsPage.countDeleteButtons();
    await adventureModelComponentsPage.clickOnLastDeleteButton();

    adventureModelDeleteDialog = new AdventureModelDeleteDialog();
    expect(await adventureModelDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureModel.delete.question');
    await adventureModelDeleteDialog.clickOnConfirmButton();

    expect(await adventureModelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
