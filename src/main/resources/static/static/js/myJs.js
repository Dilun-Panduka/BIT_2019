
/*//Nav bar properties - start//*/
$(document).ready(function () {
    $('ul.nav li.dropdown').hover(function () {
        $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn(10);
    }, function () {
        $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut(10);
    });
    /* check box field colour - selected field - start*/
    $('input[type="checkbox"]').click(function(){
        if($(this).is(":checked")){
            $('#myTable tr').bind('click', function(e) {
                $(e.currentTarget).children('td, th').css('background-color','#00FFFF');
            });
        }
        if($(this).is(":not(:checked)")){
            $('#myTable tr').bind('click', function(e) {
                $(e.currentTarget).children('td, th').css('background-color','#FAF0E6');
            });
        }
    });
    /* check box field colour - selected field - end*/
    /*Table function - start*/
    $("#myTable").DataTable({
        "lengthMenu": [[5,10,15,20,-1],[5,10,15,20,"All"]],
        "ordering": false,
        stateSave: true,
    });
     /!* Checked filed value  - end*!*/
    /* Patient and employee Nic Validation - start*/
    $("#nic").bind('keyup',function(){
        var nic = $(this).val();
        $("#dateOfBirth").val(calculateDateOfBirth(nic));
        $("#gender").val(calculateGender(nic));
    });

    /* Patient and employee Nic Validation - end*/

//prevent checkbox==null before submit -start
    $(function () {
        $('#btnSubmit').click(function (e) {
            var checked = $(':checkbox:checked').length;
            if (checked == 0) {
                alert('Atleast One Lab Test Should Be Selected');
                e.preventDefault();
            }
        });
    });
//prevent checkbox==null before submit - end

    /* multiple check box style - start*/
    $('.custom-control-input').prop('indeterminate', true)
    /* multiple check box style - end*/
});
/*//Nav bar properties - end//*/
/*spelling check - start*/
tinymce.init({
    browser_spellcheck: true,
    contextmenu: false
});
/*spelling check - end*/
/*//Filter table - start //*/

/*//Nic - data of birth - start//*/
function calculateDateOfBirth(nic) {

    var dateOfBirth = null;
    var day = null;
    var month;
    if (nic.length === 10) {
        day = +nic.substr(2, 3);
        dateOfBirth = '19' + nic.substr(0, 2) + '-';
        if (day > 500) day = day - 500;

        //<editor-fold desc="if else block">
        if (day > 335) {
            day = day - 335;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 12;
        }
        else if (day > 305) {
            day = day - 305;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 11;
        }
        else if (day > 274) {
            day = day - 274;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 10;
        }
        else if (day > 244) {
            day = day - 244;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 9;
        }
        else if (day > 213) {
            day = day - 213;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 8;
        }
        else if (day > 182) {
            day = day - 182;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 7;
        }
        else if (day > 152) {
            day = day - 152;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 6;
        }
        else if (day > 121) {
            day = day - 121;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 5;
        }
        else if (day > 91) {
            day = day - 91;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 4;
        }
        else if (day > 60) {
            day = day - 60;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 3;
        }
        else if (day < 32) {
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            day = day;
            month = 1;
        }
        else if (day > 31) {
            day = day - 31;
            if (day.toLocaleString().length === 1){    day = '0'+ day;}
            month = 2;
        }
        //</editor-fold>
        if (month.toLocaleString().length === 2) {

            dateOfBirth = dateOfBirth + month + '-' + day;
        } else {

            dateOfBirth = dateOfBirth + '0' + month + '-' + day;
        }



    } else if (nic.length === 12) {
        dateOfBirth = nic.substr(0, 4) + '-';
        day = +nic.substr(4, 3);
        if (day > 500) day = day - 500;
        //<editor-fold desc="if else block">
        if (day > 335) {
            day = day - 335;
            month = 12;
        }
        else if (day > 305) {
            day = day - 305;
            month = 11;
        }
        else if (day > 274) {
            day = day - 274;
            month = 10;
        }
        else if (day > 244) {
            day = day - 244;
            month = 9;
        }
        else if (day > 213) {
            day = day - 213;
            month = 8;
        }
        else if (day > 182) {
            day = day - 182;
            month = 7;
        }
        else if (day > 152) {
            day = day - 152;
            month = 6;
        }
        else if (day > 121) {
            day = day - 121;
            month = 5;
        }
        else if (day > 91) {
            day = day - 91;
            month = 4;
        }
        else if (day > 60) {
            day = day - 60;
            month = 3;
        }
        else if (day < 32) {
            month = 1;
        }
        else if (day > 31) {
            day = day - 31;
            month = 2;
        }
        //</editor-fold>
        if (month.toLocaleString().length === 2) {
            dateOfBirth = dateOfBirth + month + '-' + day;
        } else {
            dateOfBirth = dateOfBirth + '0' + month + '-' + day;
        }
    }
    return dateOfBirth;
}
/*//Nic - data of birth - end//*/
/*//Nic - gender - start//*/
function calculateGender(nic) {
    var gender = null;
    if (nic.length === 10) {
        if (+nic.substr(2, 3) < 500) gender = 'MALE';
        else gender = 'FEMALE';

    } else if (nic.length === 12) {
        if (+nic.substr(4, 3) < 500) gender = 'MALE';
        else gender = 'FEMALE';
    }
    return gender;
}
/*//Nic - gender - end//*/


