/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    AdventureAccountCharacterComponentsPage,
    AdventureAccountCharacterDeleteDialog,
    AdventureAccountCharacterUpdatePage
} from './adventure-account-character.page-object';

const expect = chai.expect;

describe('AdventureAccountCharacter e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let adventureAccountCharacterUpdatePage: AdventureAccountCharacterUpdatePage;
    let adventureAccountCharacterComponentsPage: AdventureAccountCharacterComponentsPage;
    let adventureAccountCharacterDeleteDialog: AdventureAccountCharacterDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AdventureAccountCharacters', async () => {
        await navBarPage.goToEntity('adventure-account-character');
        adventureAccountCharacterComponentsPage = new AdventureAccountCharacterComponentsPage();
        await browser.wait(ec.visibilityOf(adventureAccountCharacterComponentsPage.title), 5000);
        expect(await adventureAccountCharacterComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureAccountCharacter.home.title');
    });

    it('should load create AdventureAccountCharacter page', async () => {
        await adventureAccountCharacterComponentsPage.clickOnCreateButton();
        adventureAccountCharacterUpdatePage = new AdventureAccountCharacterUpdatePage();
        expect(await adventureAccountCharacterUpdatePage.getPageTitle()).to.eq(
            'adventureGatewayApp.adventureAccountCharacter.home.createOrEditLabel'
        );
        await adventureAccountCharacterUpdatePage.cancel();
    });

    it('should create and save AdventureAccountCharacters', async () => {
        const nbButtonsBeforeCreate = await adventureAccountCharacterComponentsPage.countDeleteButtons();

        await adventureAccountCharacterComponentsPage.clickOnCreateButton();
        await promise.all([
            adventureAccountCharacterUpdatePage.setNicknameInput('nickname'),
            adventureAccountCharacterUpdatePage.adventureInventoryCharSelectLastOption(),
            adventureAccountCharacterUpdatePage.adventureCharacteristicSelectLastOption(),
            adventureAccountCharacterUpdatePage.adventureRaceSelectLastOption()
            // adventureAccountCharacterUpdatePage.adventureSkillSelectLastOption(),
        ]);
        expect(await adventureAccountCharacterUpdatePage.getNicknameInput()).to.eq('nickname');
        const selectedGender = adventureAccountCharacterUpdatePage.getGenderInput();
        if (await selectedGender.isSelected()) {
            await adventureAccountCharacterUpdatePage.getGenderInput().click();
            expect(await adventureAccountCharacterUpdatePage.getGenderInput().isSelected()).to.be.false;
        } else {
            await adventureAccountCharacterUpdatePage.getGenderInput().click();
            expect(await adventureAccountCharacterUpdatePage.getGenderInput().isSelected()).to.be.true;
        }
        await adventureAccountCharacterUpdatePage.save();
        expect(await adventureAccountCharacterUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await adventureAccountCharacterComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AdventureAccountCharacter', async () => {
        const nbButtonsBeforeDelete = await adventureAccountCharacterComponentsPage.countDeleteButtons();
        await adventureAccountCharacterComponentsPage.clickOnLastDeleteButton();

        adventureAccountCharacterDeleteDialog = new AdventureAccountCharacterDeleteDialog();
        expect(await adventureAccountCharacterDeleteDialog.getDialogTitle()).to.eq(
            'adventureGatewayApp.adventureAccountCharacter.delete.question'
        );
        await adventureAccountCharacterDeleteDialog.clickOnConfirmButton();

        expect(await adventureAccountCharacterComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
