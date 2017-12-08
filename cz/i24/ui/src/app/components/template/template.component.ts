import {User} from '../../models/user.model';
import {NgxPermissionsService} from 'ngx-permissions';
import {AuthService} from '../../security/auth/auth.service';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TemplateService} from 'app/components/template/template.service';
import {Md5} from 'ts-md5/dist/md5';

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.css']
})
export class TemplateComponent implements OnInit {

  userInfo: User = null;
  templateValues: string[] = null;

  constructor(
    private authService: AuthService,
    private ngxPermissionService: NgxPermissionsService,
    private templateService: TemplateService,
    private router: Router
  ) {}

  ngOnInit () {
    this.templateService.userInfo.subscribe(user => this.userInfo = user);
    this.templateService.templateValues.subscribe(IconAndTitle => this.templateValues = IconAndTitle);
  }

  logout () {
    this.authService.logout().subscribe(result => {
      this.router.navigate(['/auth/login']);
    });
  }

  gravatar () : string {
    return `//www.gravatar.com/avatar/${Md5.hashStr(this.userInfo.email)}?s=64&d=mm`;
  }

}
