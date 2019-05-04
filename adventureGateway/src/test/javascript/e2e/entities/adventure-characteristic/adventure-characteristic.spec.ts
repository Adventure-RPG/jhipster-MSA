/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AdventureCharacteristicComponentsPage,
  AdventureCharacteristicDeleteDialog,
  AdventureCharacteristicUpdatePage
} from './adventure-characteristic.page-object';

const expect = chai.expect;

describe('AdventureCharacteristic e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let adventureCharacteristicUpdatePage: AdventureCharacteristicUpdatePage;
  let adventureCharacteristicComponentsPage: AdventureCharacteristicComponentsPage;
  let adventureCharacteristicDeleteDialog: AdventureCharacteristicDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AdventureCharacteristics', async () => {
    await navBarPage.goToEntity('adventure-characteristic');
    adventureCharacteristicComponentsPage = new AdventureCharacteristicComponentsPage();
    await browser.wait(ec.visibilityOf(adventureCharacteristicComponentsPage.title), 5000);
    expect(await adventureCharacteristicComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureCharacteristic.home.title');
  });

  it('should load create AdventureCharacteristic page', async () => {
    await adventureCharacteristicComponentsPage.clickOnCreateButton();
    adventureCharacteristicUpdatePage = new AdventureCharacteristicUpdatePage();
    expect(await adventureCharacteristicUpdatePage.getPageTitle()).to.eq(
      'adventureGatewayApp.adventureCharacteristic.home.createOrEditLabel'
    );
    await adventureCharacteristicUpdatePage.cancel();
  });

  it('should create and save AdventureCharacteristics', async () => {
    const nbButtonsBeforeCreate = await adventureCharacteristicComponentsPage.countDeleteButtons();

    await adventureCharacteristicComponentsPage.clickOnCreateButton();
    await promise.all([
      adventureCharacteristicUpdatePage.setStrengthInput('5'),
      adventureCharacteristicUpdatePage.setAgilityInput('5'),
      adventureCharacteristicUpdatePage.setVitalityInput('5'),
      adventureCharacteristicUpdatePage.setLuckInput('5'),
      adventureCharacteristicUpdatePage.setIntelligenceInput('5'),
      adventureCharacteristicUpdatePage.setWisdomInput('5'),
      adventureCharacteristicUpdatePage.setCharismaInput('5')
    ]);
    expect(await adventureCharacteristicUpdatePage.getStrengthInput()).to.eq('5', 'Expected strength value to be equals to 5');
    expect(await adventureCharacteristicUpdatePage.getAgilityInput()).to.eq('5', 'Expected agility value to be equals to 5');
    expect(await adventureCharacteristicUpdatePage.getVitalityInput()).to.eq('5', 'Expected vitality value to be equals to 5');
    expect(await adventureCharacteristicUpdatePage.getLuckInput()).to.eq('5', 'Expected luck value to be equals to 5');
    expect(await adventureCharacteristicUpdatePage.getIntelligenceInput()).to.eq('5', 'Expected intelligence value to be equals to 5');
    expect(await adventureCharacteristicUpdatePage.getWisdomInput()).to.eq('5', 'Expected wisdom value to be equals to 5');
    expect(await adventureCharacteristicUpdatePage.getCharismaInput()).to.eq('5', 'Expected charisma value to be equals to 5');
    await adventureCharacteristicUpdatePage.save();
    expect(await adventureCharacteristicUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await adventureCharacteristicComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AdventureCharacteristic', async () => {
    const nbButtonsBeforeDelete = await adventureCharacteristicComponentsPage.countDeleteButtons();
    await adventureCharacteristicComponentsPage.clickOnLastDeleteButton();

    adventureCharacteristicDeleteDialog = new AdventureCharacteristicDeleteDialog();
    expect(await adventureCharacteristicDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureCharacteristic.delete.question');
    await adventureCharacteristicDeleteDialog.clickOnConfirmButton();

    expect(await adventureCharacteristicComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
