import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

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
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureModelOptionsModule {}
