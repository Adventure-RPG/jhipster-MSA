import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
  AdventureInventoryCharComponent,
  AdventureInventoryCharDetailComponent,
  AdventureInventoryCharUpdateComponent,
  AdventureInventoryCharDeletePopupComponent,
  AdventureInventoryCharDeleteDialogComponent,
  adventureInventoryCharRoute,
  adventureInventoryCharPopupRoute
} from './';

const ENTITY_STATES = [...adventureInventoryCharRoute, ...adventureInventoryCharPopupRoute];

@NgModule({
  imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AdventureInventoryCharComponent,
    AdventureInventoryCharDetailComponent,
    AdventureInventoryCharUpdateComponent,
    AdventureInventoryCharDeleteDialogComponent,
    AdventureInventoryCharDeletePopupComponent
  ],
  entryComponents: [
    AdventureInventoryCharComponent,
    AdventureInventoryCharUpdateComponent,
    AdventureInventoryCharDeleteDialogComponent,
    AdventureInventoryCharDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureInventoryCharModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
