import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureSkillComponent,
    AdventureSkillDetailComponent,
    AdventureSkillUpdateComponent,
    AdventureSkillDeletePopupComponent,
    AdventureSkillDeleteDialogComponent,
    adventureSkillRoute,
    adventureSkillPopupRoute
} from './';

const ENTITY_STATES = [...adventureSkillRoute, ...adventureSkillPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureSkillComponent,
        AdventureSkillDetailComponent,
        AdventureSkillUpdateComponent,
        AdventureSkillDeleteDialogComponent,
        AdventureSkillDeletePopupComponent
    ],
    entryComponents: [
        AdventureSkillComponent,
        AdventureSkillUpdateComponent,
        AdventureSkillDeleteDialogComponent,
        AdventureSkillDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureSkillModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
