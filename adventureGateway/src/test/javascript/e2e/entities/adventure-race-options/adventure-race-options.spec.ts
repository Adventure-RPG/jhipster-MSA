/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    AdventureRaceOptionsComponentsPage,
    AdventureRaceOptionsDeleteDialog,
    AdventureRaceOptionsUpdatePage
} from './adventure-race-options.page-object';

const expect = chai.expect;

describe('AdventureRaceOptions e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let adventureRaceOptionsUpdatePage: AdventureRaceOptionsUpdatePage;
    let adventureRaceOptionsComponentsPage: AdventureRaceOptionsComponentsPage;
    let adventureRaceOptionsDeleteDialog: AdventureRaceOptionsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AdventureRaceOptions', async () => {
        await navBarPage.goToEntity('adventure-race-options');
        adventureRaceOptionsComponentsPage = new AdventureRaceOptionsComponentsPage();
        expect(await adventureRaceOptionsComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureRaceOptions.home.title');
    });

    it('should load create AdventureRaceOptions page', async () => {
        await adventureRaceOptionsComponentsPage.clickOnCreateButton();
        adventureRaceOptionsUpdatePage = new AdventureRaceOptionsUpdatePage();
        expect(await adventureRaceOptionsUpdatePage.getPageTitle()).to.eq(
            'adventureGatewayApp.adventureRaceOptions.home.createOrEditLabel'
        );
        await adventureRaceOptionsUpdatePage.cancel();
    });

    it('should create and save AdventureRaceOptions', async () => {
        const nbButtonsBeforeCreate = await adventureRaceOptionsComponentsPage.countDeleteButtons();

        await adventureRaceOptionsComponentsPage.clickOnCreateButton();
        await promise.all([
            adventureRaceOptionsUpdatePage.setHeightInput('5'),
            adventureRaceOptionsUpdatePage.setWeightInput('5'),
            adventureRaceOptionsUpdatePage.adventureModelOptionsSelectLastOption()
            // adventureRaceOptionsUpdatePage.adventureModelSelectLastOption(),
        ]);
        expect(await adventureRaceOptionsUpdatePage.getHeightInput()).to.eq('5');
        expect(await adventureRaceOptionsUpdatePage.getWeightInput()).to.eq('5');
        await adventureRaceOptionsUpdatePage.save();
        expect(await adventureRaceOptionsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await adventureRaceOptionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AdventureRaceOptions', async () => {
        const nbButtonsBeforeDelete = await adventureRaceOptionsComponentsPage.countDeleteButtons();
        await adventureRaceOptionsComponentsPage.clickOnLastDeleteButton();

        adventureRaceOptionsDeleteDialog = new AdventureRaceOptionsDeleteDialog();
        expect(await adventureRaceOptionsDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureRaceOptions.delete.question');
        await adventureRaceOptionsDeleteDialog.clickOnConfirmButton();

        expect(await adventureRaceOptionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
