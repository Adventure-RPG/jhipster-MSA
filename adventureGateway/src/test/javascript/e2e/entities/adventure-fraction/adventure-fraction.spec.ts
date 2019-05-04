/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AdventureFractionComponentsPage,
  AdventureFractionDeleteDialog,
  AdventureFractionUpdatePage
} from './adventure-fraction.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AdventureFraction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let adventureFractionUpdatePage: AdventureFractionUpdatePage;
  let adventureFractionComponentsPage: AdventureFractionComponentsPage;
  let adventureFractionDeleteDialog: AdventureFractionDeleteDialog;
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

  it('should load AdventureFractions', async () => {
    await navBarPage.goToEntity('adventure-fraction');
    adventureFractionComponentsPage = new AdventureFractionComponentsPage();
    await browser.wait(ec.visibilityOf(adventureFractionComponentsPage.title), 5000);
    expect(await adventureFractionComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureFraction.home.title');
  });

  it('should load create AdventureFraction page', async () => {
    await adventureFractionComponentsPage.clickOnCreateButton();
    adventureFractionUpdatePage = new AdventureFractionUpdatePage();
    expect(await adventureFractionUpdatePage.getPageTitle()).to.eq('adventureGatewayApp.adventureFraction.home.createOrEditLabel');
    await adventureFractionUpdatePage.cancel();
  });

  it('should create and save AdventureFractions', async () => {
    const nbButtonsBeforeCreate = await adventureFractionComponentsPage.countDeleteButtons();

    await adventureFractionComponentsPage.clickOnCreateButton();
    await promise.all([
      adventureFractionUpdatePage.setNameInput('name'),
      adventureFractionUpdatePage.setDescInput('desc'),
      adventureFractionUpdatePage.setImageInput(absolutePath)
    ]);
    expect(await adventureFractionUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await adventureFractionUpdatePage.getDescInput()).to.eq('desc', 'Expected Desc value to be equals to desc');
    expect(await adventureFractionUpdatePage.getImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected Image value to be end with ' + fileNameToUpload
    );
    await adventureFractionUpdatePage.save();
    expect(await adventureFractionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await adventureFractionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AdventureFraction', async () => {
    const nbButtonsBeforeDelete = await adventureFractionComponentsPage.countDeleteButtons();
    await adventureFractionComponentsPage.clickOnLastDeleteButton();

    adventureFractionDeleteDialog = new AdventureFractionDeleteDialog();
    expect(await adventureFractionDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureFraction.delete.question');
    await adventureFractionDeleteDialog.clickOnConfirmButton();

    expect(await adventureFractionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
