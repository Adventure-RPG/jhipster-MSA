import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';

@Component({
    selector: 'jhi-adventure-inventory-char-detail',
    templateUrl: './adventure-inventory-char-detail.component.html'
})
export class AdventureInventoryCharDetailComponent implements OnInit {
    adventureInventoryChar: IAdventureInventoryChar;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureInventoryChar }) => {
            this.adventureInventoryChar = adventureInventoryChar;
        });
    }

    previousState() {
        window.history.back();
    }
}
