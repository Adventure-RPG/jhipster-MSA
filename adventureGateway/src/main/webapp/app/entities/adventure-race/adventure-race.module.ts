import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

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
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureRaceModule {}
