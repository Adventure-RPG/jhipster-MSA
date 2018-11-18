import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAdventureAttributes } from 'app/shared/model/adventure-attributes.model';
import { AdventureAttributesService } from './adventure-attributes.service';

@Component({
    selector: 'jhi-adventure-attributes-update',
    templateUrl: './adventure-attributes-update.component.html'
})
export class AdventureAttributesUpdateComponent implements OnInit {
    adventureAttributes: IAdventureAttributes;
    isSaving: boolean;

    constructor(private adventureAttributesService: AdventureAttributesService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureAttributes }) => {
            this.adventureAttributes = adventureAttributes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureAttributes.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureAttributesService.update(this.adventureAttributes));
        } else {
            this.subscribeToSaveResponse(this.adventureAttributesService.create(this.adventureAttributes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureAttributes>>) {
        result.subscribe((res: HttpResponse<IAdventureAttributes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
