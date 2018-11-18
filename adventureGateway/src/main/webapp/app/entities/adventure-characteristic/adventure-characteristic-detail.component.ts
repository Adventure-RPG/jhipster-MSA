import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';

@Component({
    selector: 'jhi-adventure-characteristic-detail',
    templateUrl: './adventure-characteristic-detail.component.html'
})
export class AdventureCharacteristicDetailComponent implements OnInit {
    adventureCharacteristic: IAdventureCharacteristic;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureCharacteristic }) => {
            this.adventureCharacteristic = adventureCharacteristic;
        });
    }

    previousState() {
        window.history.back();
    }
}
