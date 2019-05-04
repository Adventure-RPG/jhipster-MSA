import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
  AdventureModelOptionsComponent,
  AdventureModelOptionsDetailComponent,
  AdventureModelOptionsUpdateComponent,
  AdventureModelOptionsDeletePopupComponent,
  AdventureModelOptionsDeleteDialogComponent,
  adventureModelOptionsRoute,
  adventureModelOptionsPopupRoute
} from './';

const ENTITY_STATES = [...adventureModelOptionsRoute, ...adventureModelOptionsPopupRoute];

@NgModule({
  imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AdventureModelOptionsComponent,
    AdventureModelOptionsDetailComponent,
    AdventureModelOptionsUpdateComponent,
    AdventureModelOptionsDeleteDialogComponent,
    AdventureModelOptionsDeletePopupComponent
  ],
  entryComponents: [
    AdventureModelOptionsComponent,
    AdventureModelOptionsUpdateComponent,
    AdventureModelOptionsDeleteDialogComponent,
    AdventureModelOptionsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureModelOptionsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
