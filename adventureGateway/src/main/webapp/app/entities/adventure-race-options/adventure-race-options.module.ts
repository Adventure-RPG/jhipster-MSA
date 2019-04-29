import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureRaceOptionsComponent,
    AdventureRaceOptionsDetailComponent,
    AdventureRaceOptionsUpdateComponent,
    AdventureRaceOptionsDeletePopupComponent,
    AdventureRaceOptionsDeleteDialogComponent,
    adventureRaceOptionsRoute,
    adventureRaceOptionsPopupRoute
} from './';

const ENTITY_STATES = [...adventureRaceOptionsRoute, ...adventureRaceOptionsPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureRaceOptionsComponent,
        AdventureRaceOptionsDetailComponent,
        AdventureRaceOptionsUpdateComponent,
        AdventureRaceOptionsDeleteDialogComponent,
        AdventureRaceOptionsDeletePopupComponent
    ],
    entryComponents: [
        AdventureRaceOptionsComponent,
        AdventureRaceOptionsUpdateComponent,
        AdventureRaceOptionsDeleteDialogComponent,
        AdventureRaceOptionsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureRaceOptionsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
