/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AdventureModelOptionsComponentsPage,
  AdventureModelOptionsDeleteDialog,
  AdventureModelOptionsUpdatePage
} from './adventure-model-options.page-object';

const expect = chai.expect;

describe('AdventureModelOptions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let adventureModelOptionsUpdatePage: AdventureModelOptionsUpdatePage;
  let adventureModelOptionsComponentsPage: AdventureModelOptionsComponentsPage;
  let adventureModelOptionsDeleteDialog: AdventureModelOptionsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AdventureModelOptions', async () => {
    await navBarPage.goToEntity('adventure-model-options');
    adventureModelOptionsComponentsPage = new AdventureModelOptionsComponentsPage();
    await browser.wait(ec.visibilityOf(adventureModelOptionsComponentsPage.title), 5000);
    expect(await adventureModelOptionsComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureModelOptions.home.title');
  });

  it('should load create AdventureModelOptions page', async () => {
    await adventureModelOptionsComponentsPage.clickOnCreateButton();
    adventureModelOptionsUpdatePage = new AdventureModelOptionsUpdatePage();
    expect(await adventureModelOptionsUpdatePage.getPageTitle()).to.eq('adventureGatewayApp.adventureModelOptions.home.createOrEditLabel');
    await adventureModelOptionsUpdatePage.cancel();
  });

  it('should create and save AdventureModelOptions', async () => {
    const nbButtonsBeforeCreate = await adventureModelOptionsComponentsPage.countDeleteButtons();

    await adventureModelOptionsComponentsPage.clickOnCreateButton();
    await promise.all([adventureModelOptionsUpdatePage.setColorInput('color')]);
    expect(await adventureModelOptionsUpdatePage.getColorInput()).to.eq('color', 'Expected Color value to be equals to color');
    await adventureModelOptionsUpdatePage.save();
    expect(await adventureModelOptionsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await adventureModelOptionsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AdventureModelOptions', async () => {
    const nbButtonsBeforeDelete = await adventureModelOptionsComponentsPage.countDeleteButtons();
    await adventureModelOptionsComponentsPage.clickOnLastDeleteButton();

    adventureModelOptionsDeleteDialog = new AdventureModelOptionsDeleteDialog();
    expect(await adventureModelOptionsDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureModelOptions.delete.question');
    await adventureModelOptionsDeleteDialog.clickOnConfirmButton();

    expect(await adventureModelOptionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
