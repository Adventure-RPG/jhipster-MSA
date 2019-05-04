import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
  AdventureFractionComponent,
  AdventureFractionDetailComponent,
  AdventureFractionUpdateComponent,
  AdventureFractionDeletePopupComponent,
  AdventureFractionDeleteDialogComponent,
  adventureFractionRoute,
  adventureFractionPopupRoute
} from './';

const ENTITY_STATES = [...adventureFractionRoute, ...adventureFractionPopupRoute];

@NgModule({
  imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AdventureFractionComponent,
    AdventureFractionDetailComponent,
    AdventureFractionUpdateComponent,
    AdventureFractionDeleteDialogComponent,
    AdventureFractionDeletePopupComponent
  ],
  entryComponents: [
    AdventureFractionComponent,
    AdventureFractionUpdateComponent,
    AdventureFractionDeleteDialogComponent,
    AdventureFractionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureFractionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
