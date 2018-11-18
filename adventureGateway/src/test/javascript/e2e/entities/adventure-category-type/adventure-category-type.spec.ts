/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    AdventureCategoryTypeComponentsPage,
    AdventureCategoryTypeDeleteDialog,
    AdventureCategoryTypeUpdatePage
} from './adventure-category-type.page-object';

const expect = chai.expect;

describe('AdventureCategoryType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let adventureCategoryTypeUpdatePage: AdventureCategoryTypeUpdatePage;
    let adventureCategoryTypeComponentsPage: AdventureCategoryTypeComponentsPage;
    let adventureCategoryTypeDeleteDialog: AdventureCategoryTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AdventureCategoryTypes', async () => {
        await navBarPage.goToEntity('adventure-category-type');
        adventureCategoryTypeComponentsPage = new AdventureCategoryTypeComponentsPage();
        expect(await adventureCategoryTypeComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureCategoryType.home.title');
    });

    it('should load create AdventureCategoryType page', async () => {
        await adventureCategoryTypeComponentsPage.clickOnCreateButton();
        adventureCategoryTypeUpdatePage = new AdventureCategoryTypeUpdatePage();
        expect(await adventureCategoryTypeUpdatePage.getPageTitle()).to.eq(
            'adventureGatewayApp.adventureCategoryType.home.createOrEditLabel'
        );
        await adventureCategoryTypeUpdatePage.cancel();
    });

    it('should create and save AdventureCategoryTypes', async () => {
        const nbButtonsBeforeCreate = await adventureCategoryTypeComponentsPage.countDeleteButtons();

        await adventureCategoryTypeComponentsPage.clickOnCreateButton();
        await promise.all([adventureCategoryTypeUpdatePage.setNameInput('name'), adventureCategoryTypeUpdatePage.setDescInput('desc')]);
        expect(await adventureCategoryTypeUpdatePage.getNameInput()).to.eq('name');
        expect(await adventureCategoryTypeUpdatePage.getDescInput()).to.eq('desc');
        await adventureCategoryTypeUpdatePage.save();
        expect(await adventureCategoryTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await adventureCategoryTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AdventureCategoryType', async () => {
        const nbButtonsBeforeDelete = await adventureCategoryTypeComponentsPage.countDeleteButtons();
        await adventureCategoryTypeComponentsPage.clickOnLastDeleteButton();

        adventureCategoryTypeDeleteDialog = new AdventureCategoryTypeDeleteDialog();
        expect(await adventureCategoryTypeDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureCategoryType.delete.question');
        await adventureCategoryTypeDeleteDialog.clickOnConfirmButton();

        expect(await adventureCategoryTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
