import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureItemComponent,
    AdventureItemDetailComponent,
    AdventureItemUpdateComponent,
    AdventureItemDeletePopupComponent,
    AdventureItemDeleteDialogComponent,
    adventureItemRoute,
    adventureItemPopupRoute
} from './';

const ENTITY_STATES = [...adventureItemRoute, ...adventureItemPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureItemComponent,
        AdventureItemDetailComponent,
        AdventureItemUpdateComponent,
        AdventureItemDeleteDialogComponent,
        AdventureItemDeletePopupComponent
    ],
    entryComponents: [
        AdventureItemComponent,
        AdventureItemUpdateComponent,
        AdventureItemDeleteDialogComponent,
        AdventureItemDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureItemModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
