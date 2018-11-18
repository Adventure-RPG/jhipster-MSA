/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    AdventureInventoryCharComponentsPage,
    AdventureInventoryCharDeleteDialog,
    AdventureInventoryCharUpdatePage
} from './adventure-inventory-char.page-object';

const expect = chai.expect;

describe('AdventureInventoryChar e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let adventureInventoryCharUpdatePage: AdventureInventoryCharUpdatePage;
    let adventureInventoryCharComponentsPage: AdventureInventoryCharComponentsPage;
    let adventureInventoryCharDeleteDialog: AdventureInventoryCharDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AdventureInventoryChars', async () => {
        await navBarPage.goToEntity('adventure-inventory-char');
        adventureInventoryCharComponentsPage = new AdventureInventoryCharComponentsPage();
        expect(await adventureInventoryCharComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureInventoryChar.home.title');
    });

    it('should load create AdventureInventoryChar page', async () => {
        await adventureInventoryCharComponentsPage.clickOnCreateButton();
        adventureInventoryCharUpdatePage = new AdventureInventoryCharUpdatePage();
        expect(await adventureInventoryCharUpdatePage.getPageTitle()).to.eq(
            'adventureGatewayApp.adventureInventoryChar.home.createOrEditLabel'
        );
        await adventureInventoryCharUpdatePage.cancel();
    });

    it('should create and save AdventureInventoryChars', async () => {
        const nbButtonsBeforeCreate = await adventureInventoryCharComponentsPage.countDeleteButtons();

        await adventureInventoryCharComponentsPage.clickOnCreateButton();
        await promise.all([adventureInventoryCharUpdatePage.adventureItemSelectLastOption()]);
        await adventureInventoryCharUpdatePage.save();
        expect(await adventureInventoryCharUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await adventureInventoryCharComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AdventureInventoryChar', async () => {
        const nbButtonsBeforeDelete = await adventureInventoryCharComponentsPage.countDeleteButtons();
        await adventureInventoryCharComponentsPage.clickOnLastDeleteButton();

        adventureInventoryCharDeleteDialog = new AdventureInventoryCharDeleteDialog();
        expect(await adventureInventoryCharDeleteDialog.getDialogTitle()).to.eq(
            'adventureGatewayApp.adventureInventoryChar.delete.question'
        );
        await adventureInventoryCharDeleteDialog.clickOnConfirmButton();

        expect(await adventureInventoryCharComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
