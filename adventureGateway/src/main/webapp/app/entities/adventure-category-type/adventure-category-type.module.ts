import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureCategoryTypeComponent,
    AdventureCategoryTypeDetailComponent,
    AdventureCategoryTypeUpdateComponent,
    AdventureCategoryTypeDeletePopupComponent,
    AdventureCategoryTypeDeleteDialogComponent,
    adventureCategoryTypeRoute,
    adventureCategoryTypePopupRoute
} from './';

const ENTITY_STATES = [...adventureCategoryTypeRoute, ...adventureCategoryTypePopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureCategoryTypeComponent,
        AdventureCategoryTypeDetailComponent,
        AdventureCategoryTypeUpdateComponent,
        AdventureCategoryTypeDeleteDialogComponent,
        AdventureCategoryTypeDeletePopupComponent
    ],
    entryComponents: [
        AdventureCategoryTypeComponent,
        AdventureCategoryTypeUpdateComponent,
        AdventureCategoryTypeDeleteDialogComponent,
        AdventureCategoryTypeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureCategoryTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
