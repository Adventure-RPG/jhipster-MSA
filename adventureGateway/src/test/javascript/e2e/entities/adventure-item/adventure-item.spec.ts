/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AdventureItemComponentsPage, AdventureItemDeleteDialog, AdventureItemUpdatePage } from './adventure-item.page-object';

const expect = chai.expect;

describe('AdventureItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let adventureItemUpdatePage: AdventureItemUpdatePage;
  let adventureItemComponentsPage: AdventureItemComponentsPage;
  let adventureItemDeleteDialog: AdventureItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AdventureItems', async () => {
    await navBarPage.goToEntity('adventure-item');
    adventureItemComponentsPage = new AdventureItemComponentsPage();
    await browser.wait(ec.visibilityOf(adventureItemComponentsPage.title), 5000);
    expect(await adventureItemComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureItem.home.title');
  });

  it('should load create AdventureItem page', async () => {
    await adventureItemComponentsPage.clickOnCreateButton();
    adventureItemUpdatePage = new AdventureItemUpdatePage();
    expect(await adventureItemUpdatePage.getPageTitle()).to.eq('adventureGatewayApp.adventureItem.home.createOrEditLabel');
    await adventureItemUpdatePage.cancel();
  });

  it('should create and save AdventureItems', async () => {
    const nbButtonsBeforeCreate = await adventureItemComponentsPage.countDeleteButtons();

    await adventureItemComponentsPage.clickOnCreateButton();
    await promise.all([
      adventureItemUpdatePage.equipmentSlotSelectLastOption(),
      adventureItemUpdatePage.setPriceInput('5'),
      adventureItemUpdatePage.setWeightInput('5'),
      adventureItemUpdatePage.adventureAttributesSelectLastOption()
    ]);
    const selectedIsEquipment = adventureItemUpdatePage.getIsEquipmentInput();
    if (await selectedIsEquipment.isSelected()) {
      await adventureItemUpdatePage.getIsEquipmentInput().click();
      expect(await adventureItemUpdatePage.getIsEquipmentInput().isSelected(), 'Expected isEquipment not to be selected').to.be.false;
    } else {
      await adventureItemUpdatePage.getIsEquipmentInput().click();
      expect(await adventureItemUpdatePage.getIsEquipmentInput().isSelected(), 'Expected isEquipment to be selected').to.be.true;
    }
    expect(await adventureItemUpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');
    expect(await adventureItemUpdatePage.getWeightInput()).to.eq('5', 'Expected weight value to be equals to 5');
    await adventureItemUpdatePage.save();
    expect(await adventureItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await adventureItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AdventureItem', async () => {
    const nbButtonsBeforeDelete = await adventureItemComponentsPage.countDeleteButtons();
    await adventureItemComponentsPage.clickOnLastDeleteButton();

    adventureItemDeleteDialog = new AdventureItemDeleteDialog();
    expect(await adventureItemDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureItem.delete.question');
    await adventureItemDeleteDialog.clickOnConfirmButton();

    expect(await adventureItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
