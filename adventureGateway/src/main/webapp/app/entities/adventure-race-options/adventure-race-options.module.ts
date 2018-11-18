import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureRaceOptionsComponent,
    AdventureRaceOptionsDetailComponent,
    AdventureRaceOptionsUpdateComponent,
    AdventureRaceOptionsDeletePopupComponent,
    AdventureRaceOptionsDeleteDialogComponent,
    adventureRaceOptionsRoute,
    adventureRaceOptionsPopupRoute
} from './';

const ENTITY_STATES = [...adventureRaceOptionsRoute, ...adventureRaceOptionsPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureRaceOptionsComponent,
        AdventureRaceOptionsDetailComponent,
        AdventureRaceOptionsUpdateComponent,
        AdventureRaceOptionsDeleteDialogComponent,
        AdventureRaceOptionsDeletePopupComponent
    ],
    entryComponents: [
        AdventureRaceOptionsComponent,
        AdventureRaceOptionsUpdateComponent,
        AdventureRaceOptionsDeleteDialogComponent,
        AdventureRaceOptionsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureRaceOptionsModule {}
