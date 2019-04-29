/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AdventureRaceComponentsPage, AdventureRaceDeleteDialog, AdventureRaceUpdatePage } from './adventure-race.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AdventureRace e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let adventureRaceUpdatePage: AdventureRaceUpdatePage;
    let adventureRaceComponentsPage: AdventureRaceComponentsPage;
    let adventureRaceDeleteDialog: AdventureRaceDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AdventureRaces', async () => {
        await navBarPage.goToEntity('adventure-race');
        adventureRaceComponentsPage = new AdventureRaceComponentsPage();
        await browser.wait(ec.visibilityOf(adventureRaceComponentsPage.title), 5000);
        expect(await adventureRaceComponentsPage.getTitle()).to.eq('adventureGatewayApp.adventureRace.home.title');
    });

    it('should load create AdventureRace page', async () => {
        await adventureRaceComponentsPage.clickOnCreateButton();
        adventureRaceUpdatePage = new AdventureRaceUpdatePage();
        expect(await adventureRaceUpdatePage.getPageTitle()).to.eq('adventureGatewayApp.adventureRace.home.createOrEditLabel');
        await adventureRaceUpdatePage.cancel();
    });

    it('should create and save AdventureRaces', async () => {
        const nbButtonsBeforeCreate = await adventureRaceComponentsPage.countDeleteButtons();

        await adventureRaceComponentsPage.clickOnCreateButton();
        await promise.all([
            adventureRaceUpdatePage.setNameInput('name'),
            adventureRaceUpdatePage.setDescInput('desc'),
            adventureRaceUpdatePage.setImageInput(absolutePath),
            adventureRaceUpdatePage.adventureRaceOptionsSelectLastOption()
            // adventureRaceUpdatePage.adventureFractionSelectLastOption(),
        ]);
        expect(await adventureRaceUpdatePage.getNameInput()).to.eq('name');
        expect(await adventureRaceUpdatePage.getDescInput()).to.eq('desc');
        expect(await adventureRaceUpdatePage.getImageInput()).to.endsWith(fileNameToUpload);
        await adventureRaceUpdatePage.save();
        expect(await adventureRaceUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await adventureRaceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AdventureRace', async () => {
        const nbButtonsBeforeDelete = await adventureRaceComponentsPage.countDeleteButtons();
        await adventureRaceComponentsPage.clickOnLastDeleteButton();

        adventureRaceDeleteDialog = new AdventureRaceDeleteDialog();
        expect(await adventureRaceDeleteDialog.getDialogTitle()).to.eq('adventureGatewayApp.adventureRace.delete.question');
        await adventureRaceDeleteDialog.clickOnConfirmButton();

        expect(await adventureRaceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
