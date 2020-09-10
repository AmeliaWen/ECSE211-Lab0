$fn=10;

sf=1/0.0003975;//Scale factor to match leocad

rotate([90,0,0])scale([sf,sf,sf]){
difference(){
 roundedRectangle([0.396875,0.193675,0.193675],0.01);
 translate([0.09,0,0])roundedRectangle([0.14,0.14,0.2],0.01);
 translate([-0.09,0,0])roundedRectangle([0.14,0.14,0.2],0.01);
}
}


module roundedRectangle(s=[1,1,1],r=0){
 hull(){
  translate([-s[0]/2+r,-s[1]/2+r,0])cylinder(r=r,h=s[2],center=true);
  translate([-s[0]/2+r,s[1]/2-r,0])cylinder(r=r,h=s[2],center=true);
  translate([s[0]/2-r,-s[1]/2+r,0])cylinder(r=r,h=s[2],center=true);
  translate([s[0]/2-r,s[1]/2-r,0])cylinder(r=r,h=s[2],center=true);
 }
}
