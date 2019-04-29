/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    AdventureAttributesComponentsPage,
    AdventureAttributesDeleteDialog,
    AdventureAttributesUpdatePage
} from './adventure-attributes.page-object';

const expect = chai.expect;

describe('AdventureAttributes e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let adventureAttributesUpdatePage: AdventureAttributesUpdatePage;
    let adventureAttributesComponentsPage: AdventureAttributesComponentsPage;
    let adventureAttributesDeleteDialog: AdventureAttributesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AdventureAttributes', async () => {
        await navBarPage.goToEntity('adventure-attributes');
        adventureAttributesComponentsPage = new AdventureAttributesComponentsPage();
        await browser.wait(ec.visibilityOf(adventureAttributesComponentsPage.title), 5000);
        expect(await adventureAttributesComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureAttributes.home.title');
    });

    it('should load create AdventureAttributes page', async () => {
        await adventureAttributesComponentsPage.clickOnCreateButton();
        adventureAttributesUpdatePage = new AdventureAttributesUpdatePage();
        expect(await adventureAttributesUpdatePage.getPageTitle()).to.eq('adventureGatewayApp.adventureAttributes.home.createOrEditLabel');
        await adventureAttributesUpdatePage.cancel();
    });

    it('should create and save AdventureAttributes', async () => {
        const nbButtonsBeforeCreate = await adventureAttributesComponentsPage.countDeleteButtons();

        await adventureAttributesComponentsPage.clickOnCreateButton();
        await promise.all([
            adventureAttributesUpdatePage.setDefenceInput('5'),
            adventureAttributesUpdatePage.defenceArmorTypeSelectLastOption(),
            adventureAttributesUpdatePage.setFireResistanceInput('5'),
            adventureAttributesUpdatePage.setEarthResistanceInput('5'),
            adventureAttributesUpdatePage.setWaterResistanceInput('5'),
            adventureAttributesUpdatePage.setWindResistanceInput('5'),
            adventureAttributesUpdatePage.activeWeaponAttackDamageSelectLastOption(),
            adventureAttributesUpdatePage.setActiveWeaponAttackHitInput('5'),
            adventureAttributesUpdatePage.setActiveWeaponAttackTypeInput('5'),
            adventureAttributesUpdatePage.setSizeInput('5')
        ]);
        expect(await adventureAttributesUpdatePage.getDefenceInput()).to.eq('5');
        expect(await adventureAttributesUpdatePage.getFireResistanceInput()).to.eq('5');
        expect(await adventureAttributesUpdatePage.getEarthResistanceInput()).to.eq('5');
        expect(await adventureAttributesUpdatePage.getWaterResistanceInput()).to.eq('5');
        expect(await adventureAttributesUpdatePage.getWindResistanceInput()).to.eq('5');
        expect(await adventureAttributesUpdatePage.getActiveWeaponAttackHitInput()).to.eq('5');
        expect(await adventureAttributesUpdatePage.getActiveWeaponAttackTypeInput()).to.eq('5');
        expect(await adventureAttributesUpdatePage.getSizeInput()).to.eq('5');
        await adventureAttributesUpdatePage.save();
        expect(await adventureAttributesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await adventureAttributesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AdventureAttributes', async () => {
        const nbButtonsBeforeDelete = await adventureAttributesComponentsPage.countDeleteButtons();
        await adventureAttributesComponentsPage.clickOnLastDeleteButton();

        adventureAttributesDeleteDialog = new AdventureAttributesDeleteDialog();
        expect(await adventureAttributesDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureAttributes.delete.question');
        await adventureAttributesDeleteDialog.clickOnConfirmButton();

        expect(await adventureAttributesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
