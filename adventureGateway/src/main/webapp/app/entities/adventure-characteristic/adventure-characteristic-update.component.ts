import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';
import { AdventureCharacteristicService } from './adventure-characteristic.service';

@Component({
    selector: 'jhi-adventure-characteristic-update',
    templateUrl: './adventure-characteristic-update.component.html'
})
export class AdventureCharacteristicUpdateComponent implements OnInit {
    adventureCharacteristic: IAdventureCharacteristic;
    isSaving: boolean;

    constructor(private adventureCharacteristicService: AdventureCharacteristicService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureCharacteristic }) => {
            this.adventureCharacteristic = adventureCharacteristic;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureCharacteristic.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureCharacteristicService.update(this.adventureCharacteristic));
        } else {
            this.subscribeToSaveResponse(this.adventureCharacteristicService.create(this.adventureCharacteristic));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureCharacteristic>>) {
        result.subscribe(
            (res: HttpResponse<IAdventureCharacteristic>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
