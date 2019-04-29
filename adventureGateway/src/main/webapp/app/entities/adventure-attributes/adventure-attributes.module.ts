import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureAttributesComponent,
    AdventureAttributesDetailComponent,
    AdventureAttributesUpdateComponent,
    AdventureAttributesDeletePopupComponent,
    AdventureAttributesDeleteDialogComponent,
    adventureAttributesRoute,
    adventureAttributesPopupRoute
} from './';

const ENTITY_STATES = [...adventureAttributesRoute, ...adventureAttributesPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureAttributesComponent,
        AdventureAttributesDetailComponent,
        AdventureAttributesUpdateComponent,
        AdventureAttributesDeleteDialogComponent,
        AdventureAttributesDeletePopupComponent
    ],
    entryComponents: [
        AdventureAttributesComponent,
        AdventureAttributesUpdateComponent,
        AdventureAttributesDeleteDialogComponent,
        AdventureAttributesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureAttributesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
