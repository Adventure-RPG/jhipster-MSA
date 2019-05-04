import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
  AdventureCharacteristicComponent,
  AdventureCharacteristicDetailComponent,
  AdventureCharacteristicUpdateComponent,
  AdventureCharacteristicDeletePopupComponent,
  AdventureCharacteristicDeleteDialogComponent,
  adventureCharacteristicRoute,
  adventureCharacteristicPopupRoute
} from './';

const ENTITY_STATES = [...adventureCharacteristicRoute, ...adventureCharacteristicPopupRoute];

@NgModule({
  imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AdventureCharacteristicComponent,
    AdventureCharacteristicDetailComponent,
    AdventureCharacteristicUpdateComponent,
    AdventureCharacteristicDeleteDialogComponent,
    AdventureCharacteristicDeletePopupComponent
  ],
  entryComponents: [
    AdventureCharacteristicComponent,
    AdventureCharacteristicUpdateComponent,
    AdventureCharacteristicDeleteDialogComponent,
    AdventureCharacteristicDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureCharacteristicModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
