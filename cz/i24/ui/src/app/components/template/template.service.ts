import {User} from '../../models/user.model';
import {Observable} from 'rxjs/Observable';
import {AuthService} from '../../security/auth/auth.service';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Injectable} from '@angular/core';

@Injectable()
export class TemplateService {
  private $userInfo: BehaviorSubject<User> = new BehaviorSubject(null);
  private $templateValues: BehaviorSubject<string[]> = new BehaviorSubject(['home', 'Home']);

  public get templateValues (): BehaviorSubject<string[]> {
    return this.$templateValues;
  }

  public get userInfo (): BehaviorSubject<User> {
    return this.$userInfo;
  }

  setTitle (values: string[]) {
    this.$templateValues.next(values);
  }

  setUser (user: User) {
    this.$userInfo.next(user);
  }

  constructor(
  ) {}

}
