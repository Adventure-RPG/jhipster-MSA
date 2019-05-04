import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
  AdventureRaceComponent,
  AdventureRaceDetailComponent,
  AdventureRaceUpdateComponent,
  AdventureRaceDeletePopupComponent,
  AdventureRaceDeleteDialogComponent,
  adventureRaceRoute,
  adventureRacePopupRoute
} from './';

const ENTITY_STATES = [...adventureRaceRoute, ...adventureRacePopupRoute];

@NgModule({
  imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AdventureRaceComponent,
    AdventureRaceDetailComponent,
    AdventureRaceUpdateComponent,
    AdventureRaceDeleteDialogComponent,
    AdventureRaceDeletePopupComponent
  ],
  entryComponents: [
    AdventureRaceComponent,
    AdventureRaceUpdateComponent,
    AdventureRaceDeleteDialogComponent,
    AdventureRaceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureRaceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
