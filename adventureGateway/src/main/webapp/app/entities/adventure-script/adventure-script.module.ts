import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

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
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureScriptModule {}
