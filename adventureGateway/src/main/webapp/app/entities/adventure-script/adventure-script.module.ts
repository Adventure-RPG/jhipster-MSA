import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
  AdventureScriptComponent,
  AdventureScriptDetailComponent,
  AdventureScriptUpdateComponent,
  AdventureScriptDeletePopupComponent,
  AdventureScriptDeleteDialogComponent,
  adventureScriptRoute,
  adventureScriptPopupRoute
} from './';

const ENTITY_STATES = [...adventureScriptRoute, ...adventureScriptPopupRoute];

@NgModule({
  imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AdventureScriptComponent,
    AdventureScriptDetailComponent,
    AdventureScriptUpdateComponent,
    AdventureScriptDeleteDialogComponent,
    AdventureScriptDeletePopupComponent
  ],
  entryComponents: [
    AdventureScriptComponent,
    AdventureScriptUpdateComponent,
    AdventureScriptDeleteDialogComponent,
    AdventureScriptDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureScriptModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
