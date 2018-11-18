import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureModelComponent,
    AdventureModelDetailComponent,
    AdventureModelUpdateComponent,
    AdventureModelDeletePopupComponent,
    AdventureModelDeleteDialogComponent,
    adventureModelRoute,
    adventureModelPopupRoute
} from './';

const ENTITY_STATES = [...adventureModelRoute, ...adventureModelPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureModelComponent,
        AdventureModelDetailComponent,
        AdventureModelUpdateComponent,
        AdventureModelDeleteDialogComponent,
        AdventureModelDeletePopupComponent
    ],
    entryComponents: [
        AdventureModelComponent,
        AdventureModelUpdateComponent,
        AdventureModelDeleteDialogComponent,
        AdventureModelDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureModelModule {}
